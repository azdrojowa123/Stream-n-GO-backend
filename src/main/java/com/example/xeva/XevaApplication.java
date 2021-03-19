package com.example.xeva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = { "com.example.xeva" })
@EnableJpaRepositories
@EnableWebSecurity(debug = true)
public class XevaApplication {

	public static void main(String[] args) {
		SpringApplication.run(XevaApplication.class, args);
	}

}
