package org.example.authservice.service;


import org.example.authservice.dto.*;
import org.example.authservice.entity.UserEntity;

import org.example.authservice.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private  final  JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
           if(user.getUsername() == null){
               return  null;
           }
           return  user;

    }

    public UserLoginRespondDTO createToken(UserLoginReqestDTO user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );

            String token = jwtService.createToken(user.getUsername());
            System.out.println(token);
            return new UserLoginRespondDTO(
                    user.getUsername(),
                    null,
                    token,
                    null,
                    "success"
            );

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}


