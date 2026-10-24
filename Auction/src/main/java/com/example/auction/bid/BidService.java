package com.example.auction.bid;



import com.example.auction.auction.Auction;
import com.example.auction.auction.AuctionRepository;
import com.example.auction.auction.AuctionStatus;
import com.example.auction.dto.BidResponseDTO;
import com.example.auction.dto.PlaceBidRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class BidService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public BidService(
            AuctionRepository auctionRepository,
            BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    @Transactional
    public BidResponseDTO placeBid(
            Long auctionId,
            PlaceBidRequestDTO request) {

        System.out.println("0: Starting bid process");

        // 1. Lock the requested auction row
        Auction auction = auctionRepository.findByIdForUpdate(auctionId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Auction not found: " + auctionId));

        System.out.println("1: Auction loaded successfully");

        Instant now = Instant.now();

        System.out.println("Current UTC instant: " + now);
        System.out.println("Auction start: " + auction.getStartTime());
        System.out.println("Auction end: " + auction.getEndTime());
        System.out.println("Auction status: " + auction.getStatus());

        // 2. Check auction status
        if (auction.getStatus() == AuctionStatus.ENDED ||
                auction.getStatus() == AuctionStatus.CANCELLED) {
            throw new IllegalArgumentException("Auction is closed");
        }

        // 3. Check auction start time
        if (now.isBefore(auction.getStartTime())) {
            throw new IllegalArgumentException("Auction has not started");
        }

        // 4. Check auction end time
        if (!now.isBefore(auction.getEndTime())) {
            throw new IllegalArgumentException("Auction has ended");
        }

        // 5. Prevent the seller from bidding on their own auction
        if (auction.getSellerUsername()
                .equals(request.bidderUsername())) {
            throw new IllegalArgumentException(
                    "You cannot bid on your own auction");
        }

        // 6. Calculate the minimum acceptable bid
        BigDecimal minimumAllowed = auction.getCurrentPrice()
                .add(auction.getMinimumIncrement());

        if (request.amount().compareTo(minimumAllowed) < 0) {
            throw new IllegalArgumentException(
                    "Minimum acceptable bid is " + minimumAllowed);
        }

        // 7. Update the auction's current price
        auction.setCurrentPrice(request.amount());
        auction.setStatus(AuctionStatus.ACTIVE);

        // 8. Save the bid
        Bid bid = bidRepository.save(
                new Bid(
                        auction,
                        request.bidderUsername(),
                        request.amount()
                )
        );

        System.out.println("2: Bid saved successfully");

        // 9. Return the response
        return toDTO(bid);
    }

    @Transactional(readOnly = true)
    public List<BidResponseDTO> getBids(Long auctionId) {

        if (!auctionRepository.existsById(auctionId)) {
            throw new IllegalArgumentException(
                    "Auction not found: " + auctionId);
        }

        return bidRepository
                .findByAuction_IdOrderByAmountDescIdAsc(auctionId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private BidResponseDTO toDTO(Bid bid) {
        return new BidResponseDTO(
                bid.getId(),
                bid.getAuction().getId(),
                bid.getBidderUsername(),
                bid.getAmount(),
                bid.getCreatedAt());
    }
}
