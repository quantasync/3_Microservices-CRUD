package com.m1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Mic1Application {

	public static void main(String[] args) {
		SpringApplication.run(Mic1Application.class, args);
	}

}
