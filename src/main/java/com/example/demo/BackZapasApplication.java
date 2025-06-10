package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.mercadopago.MercadoPagoConfig;


@SpringBootApplication
@EnableJpaAuditing
public class BackZapasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackZapasApplication.class, args);
	}

}
