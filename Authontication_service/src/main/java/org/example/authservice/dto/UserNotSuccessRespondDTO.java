package org.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserNotSuccessRespondDTO implements UserRespondDTO{
    private  Boolean error;
    private  String message;

}
