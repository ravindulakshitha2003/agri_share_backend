package com.example.auction.auction;



import com.example.auction.bid.Bid;
import com.example.auction.bid.BidRepository;
import com.example.auction.dto.AuctionResponseDTO;
import com.example.auction.dto.CreateAuctionRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public AuctionService(
            AuctionRepository auctionRepository,
            BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    @Transactional
    public AuctionResponseDTO create(CreateAuctionRequestDTO request) {

        Instant now = Instant.now();

        if (!request.startTime().isAfter(now)) {
            throw new IllegalArgumentException(
                    "Start time must be in the future"
            );
        }

        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        Auction auction = new Auction(
                request.title(),
                request.startingPrice(),
                request.minimumIncrement(),
                request.startTime(),
                request.endTime(),
                request.sellerUsername() // Fixed spelling
        );

        return toDTO(auctionRepository.save(auction));
    }
    @Transactional(readOnly = true)
    public List<AuctionResponseDTO> getAll() {
        return auctionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuctionResponseDTO getById(Long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Auction not found: " + id));

        return toDTO(auction);
    }

    @Transactional
    public AuctionResponseDTO close(Long id, String sellerUsername) {

        Auction auction = auctionRepository.findByIdForUpdate(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Auction not found: " + id));

        if (!auction.getSellerUsername().equals(sellerUsername)) {
            throw new IllegalArgumentException(
                    "Only the seller can close this auction");
        }

        if (auction.getStatus() == AuctionStatus.ENDED ||
                auction.getStatus() == AuctionStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Auction is already closed");
        }

        if (Instant.now().isBefore(auction.getEndTime())) {
            throw new IllegalArgumentException(
                    "Auction end time has not been reached");
        }

        List<Bid> bids = bidRepository
                .findByAuction_IdOrderByAmountDescIdAsc(id);

        if (!bids.isEmpty()) {
            auction.setWinnerUsername(
                    bids.get(0).getBidderUsername());
        }

        auction.setStatus(AuctionStatus.ENDED);

        return toDTO(auction);
    }

    private AuctionResponseDTO toDTO(Auction auction) {
        return new AuctionResponseDTO(
                auction.getId(),
                auction.getTitle(),
                auction.getStartingPrice(),
                auction.getCurrentPrice(),
                auction.getMinimumIncrement(),
                auction.getStartTime(),
                auction.getEndTime(),
                auction.getStatus(),
                auction.getSellerUsername(),
                auction.getWinnerUsername());
    }


    @Transactional
    public void testAuctionLock() {

        System.out.println("Before database query");

        Optional<Auction> result =
                auctionRepository.findByIdForUpdate(1L);

        System.out.println("After database query");
        System.out.println("Auction found: " + result.isPresent());

        Auction auction = result.orElseThrow(
                () -> new RuntimeException("Auction not found")
        );

        System.out.println("Auction title: " + auction.getTitle());
    }
}
