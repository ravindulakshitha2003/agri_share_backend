package com.example.authontication_service.service;


import com.example.authontication_service.entity.UserEntity;
import com.example.authontication_service.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JWTService {

    private  final UserRepository userRepository;
    private   SecretKey secretKey;




    public JWTService(
            UserRepository userRepository,
            @Value("${JWT_SECRET}") String secret) {

        this.userRepository = userRepository;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String username){
            if(username == null){
                return null;
            }

            UserEntity user = userRepository.findByUsername(username).orElse(null);

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("roles",user.getRole());
        claims.put("province",user.getProvince());

            try {
                return Jwts.builder()
                        .claims(claims)
                        .subject(user.getUsername())
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis()+1000*60*30*30))
                        .signWith(secretKey)
                        .compact();
            }catch (Exception e){
                return null;
            }



    }


    public String getUsername(String token){
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            return  null;
        }


    }

}
