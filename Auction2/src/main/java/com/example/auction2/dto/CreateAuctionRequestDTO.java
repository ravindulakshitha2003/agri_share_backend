package com.example.auction2.dto;



import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;


public record CreateAuctionRequestDTO(
        @NotBlank String title,
        @NotBlank String sellerUsername,
        @NotNull @DecimalMin("0.01") BigDecimal startingPrice,
        @NotNull @DecimalMin("0.01") BigDecimal minimumIncrement,
        @NotNull Instant startTime,
        @NotNull Instant endTime
) {}
