package com.fiap.challenge.production;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ProductionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductionApiApplication.class, args);
	}

}
