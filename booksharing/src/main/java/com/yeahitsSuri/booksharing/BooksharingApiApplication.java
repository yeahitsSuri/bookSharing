package com.yeahitsSuri.booksharing;

import com.yeahitsSuri.booksharing.role.Role;
import com.yeahitsSuri.booksharing.role.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BooksharingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksharingApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepo roleRepo) {
		return args -> {
			if (roleRepo.findByName("USER").isEmpty()) {
				roleRepo.save(
								Role.builder().name("USER").build()
				);
			}
		};
	}
}
