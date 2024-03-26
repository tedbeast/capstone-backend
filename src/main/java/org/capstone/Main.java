package org.capstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}