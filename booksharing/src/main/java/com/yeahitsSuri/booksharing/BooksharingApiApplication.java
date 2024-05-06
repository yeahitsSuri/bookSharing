package com.yeahitsSuri.booksharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BooksharingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksharingApiApplication.class, args);
	}

}
