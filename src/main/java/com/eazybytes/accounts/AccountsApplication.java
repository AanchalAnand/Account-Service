package com.eazybytes.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API documentation",
				description = "Accounts microservice REST API documentation",
				version = "v1",
				contact = @Contact(
						name = "Aanchal",
						email = "aanchal@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.randomwebsite.com"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
