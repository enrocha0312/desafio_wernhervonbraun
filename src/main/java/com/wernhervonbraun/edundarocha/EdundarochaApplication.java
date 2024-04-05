package com.wernhervonbraun.edundarocha;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "Projeto Back-End Wernher von Braun",
				version = "1.0",
				description = "Projeto Back-End Wernher von braun",
				termsOfService = "ENRProducoes",
				contact = @Contact(
						name = "Eduardo N. da Rocha",
						email = "enrocha0312@gmail.com"
				),
				license = @License(
						name = "licence",
						url = "enrocha"
				)
		)
)
@SpringBootApplication
public class EdundarochaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdundarochaApplication.class, args);
	}

}
