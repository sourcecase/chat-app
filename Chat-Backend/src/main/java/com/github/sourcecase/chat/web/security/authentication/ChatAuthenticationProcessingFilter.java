package com.github.sourcecase.chat.web.security.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class ChatAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger logger = Logger.getLogger(ChatAuthenticationProcessingFilter.class.getName());

	public ChatAuthenticationProcessingFilter(String processingUrl, AuthenticationManager authenticationManager) {
		super();
		AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(processingUrl, "POST");
		this.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("attemptAuthentication came.");
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		BufferedReader reader;
		try {
			reader = request.getReader();
			JsonReader createReader = Json.createReader(reader);
			JsonObject jsonMessage = createReader.readObject();
			String username = jsonMessage.getString("username");
			String password = jsonMessage.getString("password");
			if (username == null) {
				username = "";
			}
			if (password == null) {
				password = "";
			}

			logger.info("attemptAuthentication retrieved username:" + username + " password:" + password);

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);

			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

			AuthenticationManager authenticationManager = this.getAuthenticationManager();
			return authenticationManager.authenticate(authRequest);
		} catch (IOException e) {
			logger.log(Level.INFO, "", e.getCause());
			return null;
		}
	}

}
