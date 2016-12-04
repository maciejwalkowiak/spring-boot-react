package com.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class ReactBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactBootApplication.class, args);
    }

    @Bean
    @Profile("dev")
    InitializingBean webpackRunner() {
        return () -> CompletableFuture.runAsync(() -> new ShellCommand("npm run start").executeInRelativePath("/frontend/"));
    }
}

