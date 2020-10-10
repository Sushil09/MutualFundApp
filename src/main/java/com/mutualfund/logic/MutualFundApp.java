package com.mutualfund.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"client"})
public class MutualFundApp {

    public static void main(String[] args) {
        SpringApplication.run(MutualFundApp.class, args);
    }

}
