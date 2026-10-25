package com.example.suplier_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Data

public class AuctionRequestDTO {
    private String  title;
    private String sellerUsername;
    private BigDecimal startingPrice;
    private BigDecimal minimumIncrement;
    private Instant startTime;
    private    Instant endTime;
}
