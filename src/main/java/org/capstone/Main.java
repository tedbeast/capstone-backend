package org.capstone;

<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
>>>>>>> origin/login-team
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

<<<<<<< HEAD

@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
=======
@SpringBootApplication
public class Main {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
>>>>>>> origin/login-team
