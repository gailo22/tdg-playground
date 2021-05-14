package com.example.graylog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraylogDemoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(GraylogDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GraylogDemoApplication.class, args);
		LOG.info("Hello from Spring Boot");
	}

}
