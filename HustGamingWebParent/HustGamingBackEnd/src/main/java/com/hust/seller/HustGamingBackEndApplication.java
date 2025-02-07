package com.hust.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hust.seller")
public class HustGamingBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HustGamingBackEndApplication.class, args);
	}


}
