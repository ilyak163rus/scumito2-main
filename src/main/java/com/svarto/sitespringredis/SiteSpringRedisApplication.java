package com.svarto.sitespringredis;

import com.svarto.sitespringredis.repos.ProductRepository;
import com.svarto.sitespringredis.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
//@ComponentScan
@EntityScan

public class SiteSpringRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteSpringRedisApplication.class, args);
		System.out.println("It's Workin!");
		//ProductService service1 = null;
		//service1.createTestExamples(1L, "Майка летняя", "Автор", 1000, "Самара", "Крутая майка", "ссылка", true, 1);

	}

}
