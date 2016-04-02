package com.github.sourcecase.chat.web.security.logout;

import java.util.logging.Logger;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.github.sourcecase.chat.web.ChatPathConfiguration;

public class ChatLogoutFilter extends LogoutFilter {

	private static final Logger logger = Logger.getLogger(ChatLogoutFilter.class.getName());

	public ChatLogoutFilter() {
		this(new ChatLogoutSuccessHandler(), new LogoutHandler[] { new SecurityContextLogoutHandler() });
		logger.info("ChatLogoutFilter init");
		AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(
				ChatPathConfiguration.LOGOUT_PERFORM_URL, "POST");
		this.setLogoutRequestMatcher(antPathRequestMatcher);
	}

	public ChatLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler[] handlers) {
		super(logoutSuccessHandler, handlers);
	}

}
