package com.svarto.sitespringredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

@EntityScan

public class SiteSpringRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteSpringRedisApplication.class, args);
		System.out.println("It's Workin!");
