package com.myweapon.hourglass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HourglassApplication {
	public static void main(String[] args) {
		SpringApplication.run(HourglassApplication.class, args);
	}
}
