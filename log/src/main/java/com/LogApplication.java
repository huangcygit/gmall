package com;

import com.log.LogService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LogApplication.class, args);

//        LogService log = context.getBean(LogService.class);
//        log.log();

    }

}
