package com.github.sourcecase.chat.web.security.authentication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class ChatAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = Logger.getLogger(ChatAuthenticationEntryPoint.class.getName());

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if (authException != null) {
			logger.info("Problem with " + request.getMethod() + " " + request.getRequestURL());
			if (authException instanceof InsufficientAuthenticationException) {
				logger.info("authentification not granted.");
			} else {
				logger.log(Level.INFO, "", authException);
			}
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			logger.info("everything went fine.");
		}
	}

}
