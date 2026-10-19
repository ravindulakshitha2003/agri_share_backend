package org.example.authservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequesttDTO {

    private  String username;
    private  String password;
    private  String province;
    private  String whatsapp_number;
    private  int age;
    private  String  role ;
}
