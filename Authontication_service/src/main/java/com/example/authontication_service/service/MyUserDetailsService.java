package com.example.authontication_service.service;


import com.example.authontication_service.entity.UserEntity;
import com.example.authontication_service.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    private  final UserRepository userRepository;

    public MyUserDetailsService( UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData= userRepository.findByUsername(username).orElse(null);
       if(userData.getId()== null){
           return  null;
       }
        UserDetails user = User.builder()
                .username(userData.getUsername())
                .password(userData.getPassword())
                .build();
       return  user;

    }
}
