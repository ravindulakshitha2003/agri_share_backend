package com.example.authontication_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserLoginRespondDTO {

    private  String  username;
    private   Boolean error;
    private  String accesstoken ;
    private  String refreshtoken ;
    private  String message;
}
