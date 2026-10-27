package com.example.auction2.dto;



import java.math.BigDecimal;
import java.time.Instant;

public record BidResponseDTO(
        Long id,
        Long auctionId,
        String bidderUsername,
        BigDecimal amount,
        Instant createdAt
) {}
