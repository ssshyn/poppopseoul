package com.seoulmate.poppopseoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PoppopseoulApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoppopseoulApplication.class, args);
	}

}
