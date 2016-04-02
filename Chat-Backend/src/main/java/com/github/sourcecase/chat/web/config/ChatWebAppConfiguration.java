package com.github.sourcecase.chat.web.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.github.sourcecase.chat.web.controller.ChatWebSocketHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket
@EnableAspectJAutoProxy
@Order(-1)
@ComponentScan(basePackages = "com.github.sourcecase.chat")
public class ChatWebAppConfiguration extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	private static final Logger logger = Logger.getLogger(ChatWebAppConfiguration.class.getName());

	public ChatWebAppConfiguration() {
		logger.log(Level.SEVERE, "init constructor.");
	}

	@Autowired
	private ChatWebSocketHandler chatWebSocketHandler;

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
		registry.addResourceHandler("/javascript/**").addResourceLocations("/WEB-INF/javascript/")
				.setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatWebSocketHandler, "/ChatWebSocketNeu").setHandshakeHandler(handshakeHandler())
				.setAllowedOrigins("*");
	}

	@Bean
	public DefaultHandshakeHandler handshakeHandler() {

		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
		policy.setInputBufferSize(8192);
		policy.setIdleTimeout(600000);

		return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
	}

}