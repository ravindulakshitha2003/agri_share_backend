package com.example.auction.auction;


package com.example.auction.scheduler;

import com.example.auction.service.AuctionExpiryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuctionExpiryScheduler {

    private final AuctionExpiryService auctionExpiryService;

    public AuctionExpiryScheduler(
            AuctionExpiryService auctionExpiryService
    ) {
        this.auctionExpiryService = auctionExpiryService;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkAuctions() {
        auctionExpiryService.checkExpiredAuctions();
    }
}