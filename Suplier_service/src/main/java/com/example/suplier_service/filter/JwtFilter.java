package com.example.suplier_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private  final JWTService jwtService;
    private  final UserRepository userRepository;

    public JwtFilter(JWTService jwtService, UserRepository userRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        ;

        if(header ==  null){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.split("Bearer ")[1];
        if(token == null){
            filterChain.doFilter(request,response);
            return;
        }

        String username = jwtService.getUsername(token);
        if(username == null){
            filterChain.doFilter(request,response);
            return;
        }


        UserEntity userData = userRepository.findByUsername(username).orElse(null);


        if(userData == null){
            filterChain.doFilter(request,response);
            return;
        }


        if(SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        UserDetails user = User.builder()
                .username(userData.getUsername())
                .password(userData.getPassword())
                .build();

        UsernamePasswordAuthenticationToken newtoken = new UsernamePasswordAuthenticationToken(userData,null,null);
        newtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(newtoken);


        filterChain.doFilter(request,response);


    }
}
