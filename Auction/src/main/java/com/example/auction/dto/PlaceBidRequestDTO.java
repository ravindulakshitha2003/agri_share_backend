package com.example.auction.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PlaceBidRequestDTO(
        @NotBlank String bidderUsername,
        @NotNull @DecimalMin("0.01") BigDecimal amount
) {}
