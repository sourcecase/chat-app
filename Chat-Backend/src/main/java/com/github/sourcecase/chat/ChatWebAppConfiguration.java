package com.github.sourcecase.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ChatWebAppConfiguration extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChatWebAppConfiguration.class, args);
	}

}