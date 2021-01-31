package com.test.els;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ElsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElsApplication.class, args);
	}

}
