package com.mithunnirmal.merch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MerchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchApplication.class, args);
	}

}
