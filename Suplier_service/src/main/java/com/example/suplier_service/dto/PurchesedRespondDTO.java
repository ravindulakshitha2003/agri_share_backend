package com.example.suplier_service.dto;


import lombok.Data;

@Data
public class PurchesedRespondDTO {
    private boolean error;
    private  String msg;

    public PurchesedRespondDTO(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
    }
}
