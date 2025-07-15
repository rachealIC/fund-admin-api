package com.racheal.fundadmin.fundadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class FundadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundadminApplication.class, args);
	}

}
