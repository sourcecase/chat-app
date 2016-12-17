package com.github.sourcecase.chat.web.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatSecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	private static final Logger logger = Logger.getLogger(ChatSecurityWebApplicationInitializer.class.getName());

	public ChatSecurityWebApplicationInitializer() {
		logger.log(Level.SEVERE, "init constructor");
	}
}
