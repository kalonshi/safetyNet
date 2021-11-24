package com.spl.safetyNet;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SafetyNetApplication {

	private static final Logger logger = LogManager.getLogger(SafetyNetApplication.class.getName());

	public static void main(String[] args) throws IOException {

		logger.debug("This is  a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is a error message");
		logger.fatal("This is  a fatal message");

		SpringApplication.run(SafetyNetApplication.class, args);

	}

}
