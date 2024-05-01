package com.svarto.sitespringredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
@EntityScan

public class SiteSpringRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteSpringRedisApplication.class, args);
		System.out.println("It's Workin!");

	}
}
