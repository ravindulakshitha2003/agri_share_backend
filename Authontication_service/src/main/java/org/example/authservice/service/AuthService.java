package org.example.authservice.service;


import org.example.authservice.dto.UserNotSuccessRespondDTO;
import org.example.authservice.dto.UserRequesttDTO;
import org.example.authservice.dto.UserRespondDTO;
import org.example.authservice.dto.UserSuccessRespondDTO;
import org.example.authservice.entity.UserEntity;

import org.example.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRespondDTO userRegister (UserRequesttDTO userRequesttDTO){
        UserEntity  user = new UserEntity(
                userRequesttDTO.getUsername(),
                passwordEncoder.encode(userRequesttDTO.getPassword()),
                userRequesttDTO.getProvince(),
                userRequesttDTO.getWhatsapp_number(),
                userRequesttDTO.getAge(),
                "user"
        );

       UserEntity respond= userRepository.save(user);
       if(respond.getId() != null){
           return  new UserSuccessRespondDTO(respond.getUsername(), respond.getPassword(), respond.getProvince(), respond.getWhatsappnumber(), respond.getAge(), respond.getRole());
       }
        return  new UserNotSuccessRespondDTO();
    }

    public  UserEntity getUserByUsername(String username){
           UserEntity user= userRepository.findByUsername(username).orElse(null);
           if(user.getId() == null){
               return  null;
           }
           return  user;

    }
}


