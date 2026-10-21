
package com.example.suplier_service.filter;

import com.example.suplier_service.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication
        .WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JwtFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // No Bearer token: let Spring Security handle the request.
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();

        if (token.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing JWT");
            return;
        }

        try {
            // Verify the token and read its claims.
            Claims claims = jwtService.getClaims(token);

            String username = claims.getSubject();
            String role = claims.get("roles", String.class);

            if (username == null || username.isBlank()) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT");
                return;
            }

            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                // Convert the role claim into a Spring Security authority.
                List<SimpleGrantedAuthority> authorities =
                        (role == null || role.isBlank())
                                ? List.of()
                                : List.of(new SimpleGrantedAuthority(
                                role.startsWith("ROLE_")
                                        ? role
                                        : "ROLE_" + role));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities);

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT");
        }
    }
}
