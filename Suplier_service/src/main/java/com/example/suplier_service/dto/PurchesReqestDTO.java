package com.example.suplier_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PurchesReqestDTO {
    private  String productid;
    private  String username;
    private  long quntitiy;
    private  String unit;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getQuntitiy() {
        return quntitiy;
    }

    public void setQuntitiy(long quntitiy) {
        this.quntitiy = quntitiy;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
