package com.github.sourcecase.chat.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.github.sourcecase.chat.web.config.ChatWebAppConfiguration;
import com.github.sourcecase.chat.web.security.config.ChatWebAppSecurityConfigurerAdapter;

public class ChatWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static final String CHAT_ROOT_URL_PATH = "/chat";

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ChatWebAppSecurityConfigurerAdapter.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ChatWebAppConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { CHAT_ROOT_URL_PATH + "/*" };
	}

	public WebApplicationContext createWebApplicationContextForTesting() {
		return this.createServletApplicationContext();
	}

}