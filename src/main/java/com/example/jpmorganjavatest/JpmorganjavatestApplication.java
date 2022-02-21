package com.example.jpmorganjavatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JpmorganjavatestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpmorganjavatestApplication.class, args);
	}

}
