package com.example.suplier_service.entitiy;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor

@Data
@Document(collection = "Goods")
public class ProductEntitiy {

    @Id
    private  String id;
    private String category;
    private String username;
    private String productName;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
    private String condition;
    private String packaging;
    private BigDecimal minimumQuantity;
    private String location;
    private LocalDate availableDate;

    public ProductEntitiy(String category, String username, String productName, BigDecimal quantity, String unit, BigDecimal price, String condition, String packaging, BigDecimal minimumQuantity, String location, LocalDate availableDate, LocalTime availableTime, LocalDate deadlineDate, LocalTime deadlineTime, String freshness, String storage, String urgency, Boolean canDeliver, Boolean priceNegotiable, String description, boolean auction, String auctionid) {
        this.category = category;
        this.username = username;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.condition = condition;
        this.packaging = packaging;
        this.minimumQuantity = minimumQuantity;
        this.location = location;
        this.availableDate = availableDate;
        this.availableTime = availableTime;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.freshness = freshness;
        this.storage = storage;
        this.urgency = urgency;
        this.canDeliver = canDeliver;
        this.priceNegotiable = priceNegotiable;
        this.description = description;
        this.auction = auction;
        this.auctionid = auctionid;
    }

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
    private  String auctionid = null ;

}
