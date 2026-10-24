package com.example.suplier_service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AuctionRequestDTO {
    private String  title;
    private String sellerUsername;
    private BigDecimal startingPrice;
    private BigDecimal minimumIncrement;
    private Instant startTime;
    private    Instant endTime;
}
