package com.knigovert.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Володимир on 05.04.2018.
 */

@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    public static void main(String... args) {
        SpringApplication.run(com.knigovert.review.Application.class, args);
    }
}
