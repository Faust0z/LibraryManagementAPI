package com.faust0z.BookLibraryAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class BookLibraryAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookLibraryAPIApplication.class, args);
    }

}
