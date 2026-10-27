package com.example.auction2.scheduler;



import com.example.auction2.auction.AuctionExpiryService;
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