package com.example.suplier_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@Data
public class ProductRequestDTO   {


    private String category;
    private String productName;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
    private String condition;
    private String packaging;
    private BigDecimal minimumQuantity;
    private String location;
    private LocalDate availableDate;
    private LocalTime availableTime;
    private LocalDate deadlineDate;
    private LocalTime deadlineTime;
    private String freshness;
    private String storage;
    private String urgency;
    private Boolean canDeliver;
    private Boolean priceNegotiable;
    private String description;
    private boolean auction = false  ;

}
