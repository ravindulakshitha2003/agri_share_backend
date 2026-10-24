package com.example.suplier_service.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebConfiguration {
    @Bean
    public WebClient.Builder webClientBuilder() {

        return WebClient.builder();
    }



    @Bean
    public WebClient AuctionWebClient() {

        return webClientBuilder().baseUrl("http://localhost:8005/api/v1").build();
    }

}


