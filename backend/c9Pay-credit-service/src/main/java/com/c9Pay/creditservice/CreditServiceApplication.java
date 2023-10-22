package com.c9Pay.creditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class CreditServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditServiceApplication.class, args);
	}

}
