package com.example.authontication_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSuccessRespondDTO implements  UserRespondDTO{
    private  String username;
    private  String password;
    private  String province;
    private  String whatsapp_number;
    private  int age;
    private  String  role ;
}
