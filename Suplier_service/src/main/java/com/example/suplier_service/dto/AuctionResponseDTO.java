package com.example.suplier_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Data
public class AuctionResponseDTO {

   private Long id;
    private String title;
    private   BigDecimal startingPrice;
    private  BigDecimal currentPrice;
    private  BigDecimal minimumIncrement;
    private  Instant startTime;
    private   Instant endTime;
    private  AuctionStatus status;
    private String sellerUsername;
    private String winnerUsername;
}
