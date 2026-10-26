package com.example.auction.auction;



import com.example.auction.bid.Bid;
import com.example.auction.bid.BidRepository;
import com.example.auction.dto.AuctionEndedEvent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;


@Service
public class AuctionExpiryService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public AuctionExpiryService(
            AuctionRepository auctionRepository,
            BidRepository bidRepository,
            OutboxEventRepository outboxRepository,
            ObjectMapper objectMapper
    ) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    public void checkExpiredAuctions() {

        Instant now = Instant.now();

        var expiredIds = auctionRepository.findExpiredAuctions(
                AuctionStatus.ACTIVE,
                now
        );

        for (Auction auction : expiredIds) {
            try {
                finalizeAuction(auction.getId());
            } catch (Exception ex) {
                System.err.println(
                        "Failed to finalize auction "
                                + auction.getId()
                                + ": " + ex.getMessage()
                );
            }
        }
    }

    @Transactional
    public void finalizeAuction(Long auctionId)
            throws Exception {

        Auction auction = auctionRepository
                .findByIdForUpdate(auctionId)
                .orElse(null);

        if (auction == null) {
            return;
        }

        // Another execution may already have ended this auction.
        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            return;
        }

        // Never end an auction before its actual end time.
        Instant now = Instant.now();

        if (auction.getEndTime().isAfter(now)) {
            return;
        }

        Bid winningBid = bidRepository
                .findTopByAuctionIdOrderByAmountDesc(auctionId)
                .orElse(null);

        String winnerUsername = winningBid == null
                ? null
                : winningBid.getBidderUsername();

        // Finalize the business state.
        auction.setWinnerUsername(winnerUsername);
        auction.setStatus(AuctionStatus.ENDED);
        auctionRepository.save(auction);

        // Create a stable ID so retries refer to the same event.
        String eventId = "auction-ended-" + auctionId;

        AuctionEndedEvent event = new AuctionEndedEvent(
                eventId,
                auction.getId(),
                auction.getTitle(),
                auction.getSellerUsername(),
                winnerUsername,
                winningBid == null
                        ? null
                        : winningBid.getAmount(),
                now
        );

        String payload = objectMapper.writeValueAsString(event);

        // Saved in the SAME transaction as the auction update.
        outboxRepository.save(
                new com.example.auction.entity.OutboxEvent(
                        eventId,
                        "AUCTION_ENDED",
                        payload
                )
        );
    }
}
