package com.example.auction2.bid;


import com.example.auction2.dto.BidResponseDTO;
import com.example.auction2.dto.PlaceBidRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions/{auctionId}/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public BidResponseDTO placeBid(
            @PathVariable Long auctionId,
            @Valid @RequestBody PlaceBidRequestDTO request) {
        return bidService.placeBid(auctionId, request);
    }

    @GetMapping
    public List<BidResponseDTO> getBids(
            @PathVariable Long auctionId) {
        return bidService.getBids(auctionId);
    }
}
