package com.example.auction2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Auction2Application {

    public static void main(String[] args) {
        SpringApplication.run(Auction2Application.class, args);
    }

}
