package com.example.auction.dto;


import java.math.BigDecimal;
import java.time.Instant;

public record AuctionEndedEvent(
        String eventId,
        Long auctionId,
        String title,
        String sellerUsername,
        String winnerUsername,
        BigDecimal winningPrice,
        Instant endedAt
) {}