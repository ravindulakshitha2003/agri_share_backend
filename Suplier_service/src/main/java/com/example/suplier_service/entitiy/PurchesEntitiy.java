package com.example.suplier_service.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "purches")
public class PurchesEntitiy {
    @Id
    private String id;

    public PurchesEntitiy(String productid, String username, long quntitiy, String unit) {
        this.productid = productid;
        this.username = username;
        this.quntitiy = quntitiy;
        this.unit = unit;
    }

    private  String productid;
    private  String username;
    private  long quntitiy;
    private  String unit;
}
