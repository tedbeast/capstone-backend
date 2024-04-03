package org.capstone;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@Slf4j
@SpringBootApplication
public class Main {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }

    // Needed for the 2nd API service
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

