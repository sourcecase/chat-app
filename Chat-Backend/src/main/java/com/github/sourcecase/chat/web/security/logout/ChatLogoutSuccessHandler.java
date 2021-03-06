package com.github.sourcecase.chat.web.security.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class ChatLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final Logger logger = Logger.getLogger(ChatLogoutSuccessHandler.class.getName());

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("onLogoutSuccess called");
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
