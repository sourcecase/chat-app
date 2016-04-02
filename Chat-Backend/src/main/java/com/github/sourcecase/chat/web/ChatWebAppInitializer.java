package com.github.sourcecase.chat.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.github.sourcecase.chat.web.config.ChatWebAppConfiguration;
import com.github.sourcecase.chat.web.security.config.ChatWebAppSecurityConfigurerAdapter;

public class ChatWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ChatWebAppSecurityConfigurerAdapter.class, ChatWebAppConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	public WebApplicationContext createWebApplicationContextForTesting() {
		return this.createServletApplicationContext();
	}

}