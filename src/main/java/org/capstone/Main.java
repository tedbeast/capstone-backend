package org.capstone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
