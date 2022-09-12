package com.example.newauthtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class NewAuthTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewAuthTestApplication.class, args);
    }

}
