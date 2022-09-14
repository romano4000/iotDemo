package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type Micro streaming analytics application.
 */
@SpringBootApplication
@EnableScheduling
public class MicroStreamingAnalyticsApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MicroStreamingAnalyticsApplication.class, args);
	}

}
