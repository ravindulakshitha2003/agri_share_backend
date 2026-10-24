package com.example.auction.dto;




import com.example.auction.auction.AuctionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record AuctionResponseDTO(
        Long id,
        String title,
        BigDecimal startingPrice,
        BigDecimal currentPrice,
        BigDecimal minimumIncrement,
        Instant startTime,
        Instant endTime,
        AuctionStatus status,
        String sellerUsername,
        String winnerUsername
) {}
