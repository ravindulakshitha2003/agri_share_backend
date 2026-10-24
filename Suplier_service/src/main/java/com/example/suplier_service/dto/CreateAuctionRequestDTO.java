package com.example.suplier_service.dto;



import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAuctionRequestDTO extends ProductRequestDTO {

        private String  title;
        private String sellerUsername;
        private BigDecimal startingPrice;
        private BigDecimal minimumIncrement;
        private Instant startTime;
        private    Instant endTime;

    public CreateAuctionRequestDTO(String category, String productName, BigDecimal quantity, String unit, BigDecimal price, String condition, String packaging, BigDecimal minimumQuantity, String location, LocalDate availableDate, LocalTime availableTime, LocalDate deadlineDate, LocalTime deadlineTime, String freshness, String storage, String urgency, Boolean canDeliver, Boolean priceNegotiable, String description, boolean auction, String title, String sellerUsername, BigDecimal startingPrice, BigDecimal minimumIncrement, Instant startTime, Instant endTime) {
        super(category, productName, quantity, unit, price, condition, packaging, minimumQuantity, location, availableDate, availableTime, deadlineDate, deadlineTime, freshness, storage, urgency, canDeliver, priceNegotiable, description, auction);
        this.title = title;
        this.sellerUsername = sellerUsername;
        this.startingPrice = startingPrice;
        this.minimumIncrement = minimumIncrement;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
