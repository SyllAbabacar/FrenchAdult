package com.fr.register;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fr.register.dto.UserDto;
import com.fr.register.entities.Gender;
import com.fr.register.entities.User;
import com.fr.register.repository.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FrenchAdultRegisterApplication {

	@Bean
	public UserDto userDto() {
		return new UserDto();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FrenchAdultRegisterApplication.class, args);

	}

	@Bean
	CommandLineRunner start(UserRepository userRepository) {
		return args -> {
			
			LocalDate localDate = LocalDate.of(1995, 02, 20);
			userRepository.save(new User(null, "Faty Syll", java.sql.Date.valueOf(localDate), "France",
					"+33 1 23 45 61 89", Gender.F));
			
			localDate = LocalDate.of(1997, 10, 20);
			userRepository.save(new User(null, "Modou Ndiaye", java.sql.Date.valueOf(localDate), "France",
					"+33 1 23 43 67 88", Gender.M));
			
			localDate = LocalDate.of(1990, 04, 16);
			userRepository.save(new User(null, "Massamba Diop", java.sql.Date.valueOf(localDate), "France",
					"+33 1 23 65 67 98", Gender.M));

		};

	}

}
