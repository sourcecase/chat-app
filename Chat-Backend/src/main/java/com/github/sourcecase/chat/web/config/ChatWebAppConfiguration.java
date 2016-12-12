package com.github.sourcecase.chat.web.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.github.sourcecase.chat.web.ChatPathConfiguration;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@Order(-1)
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.github.sourcecase.chat")
public class ChatWebAppConfiguration extends WebMvcConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(ChatWebAppConfiguration.class.getName());

	public ChatWebAppConfiguration() {
		logger.log(Level.SEVERE, "init constructor.");
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(ChatPathConfiguration.CHAT_ROOT_DATA_PATH + "/javascript/**")
				.addResourceLocations("/WEB-INF" + ChatPathConfiguration.CHAT_ROOT_DATA_PATH + "/javascript/")
				.setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChatWebAppConfiguration.class, args);
	}

}