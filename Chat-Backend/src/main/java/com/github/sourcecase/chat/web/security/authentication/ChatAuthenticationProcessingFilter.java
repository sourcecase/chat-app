package com.github.sourcecase.chat.web.security.authentication;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger logger = Logger.getLogger(ChatAuthenticationProcessingFilter.class.getName());
	private final ChatDTOFactory chatDTOFactory;

	public ChatAuthenticationProcessingFilter(AuthenticationManager authenticationManager,
											  ChatDTOFactory chatDTOFactory) {
		super();
		this.chatDTOFactory = chatDTOFactory;
		AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(com.github.sourcecase.chat.web.ChatPathConfiguration.LOGIN_VALIDATE_URL, "POST");
		this.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest servletRequest, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("attemptAuthentication came.");
		if (!servletRequest.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + servletRequest.getMethod());
		}

		try {
			InputStream bodyStream = servletRequest.getInputStream();
			String body = IOUtils.toString(bodyStream);
			logger.info("body stream: " + body);
			ChatUserLoginDTO userLoginDto = chatDTOFactory.createFromJson(ChatUserLoginDTO.class, body);
			logger.info("registering retrieved username:" + userLoginDto.getName() + " password:"
					+ userLoginDto.getPassword());

			final String name = userLoginDto.getName();
			final String password = userLoginDto.getPassword();
			logger.info("attemptAuthentication retrieved username:" + name + " password:" + password);

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(name, password);

			authRequest.setDetails(authenticationDetailsSource.buildDetails(servletRequest));

			AuthenticationManager authenticationManager = this.getAuthenticationManager();
			return authenticationManager.authenticate(authRequest);
		} catch (IOException e) {
			logger.log(Level.INFO, "", e.getCause());
			return null;
		}
	}

}
