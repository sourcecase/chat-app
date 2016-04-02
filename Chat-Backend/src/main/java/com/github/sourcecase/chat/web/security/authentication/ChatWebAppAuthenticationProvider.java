package com.github.sourcecase.chat.web.security.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserService;

@Component
public class ChatWebAppAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(ChatWebAppAuthenticationProvider.class.getName());

	public ChatWebAppAuthenticationProvider() {
		logger.log(Level.INFO, "ChatWebAppAuthenticationProvider constructor called.");
	}

	@Autowired
	private ChatUserService chatUserService;

	@Autowired
	private ChatDTOFactory chatDTOFactory;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.log(Level.SEVERE, "authenticate");

		final String userName = authentication.getName();
		if ((userName == null) || userName.equals("")) {
			logger.log(Level.SEVERE, "user name was empty");
			return null;
		}

		String authority = "USER";
		String password = (String) authentication.getCredentials();

		ChatUserLoginDTO createChatUserLoginDTO = chatDTOFactory.createChatUserLoginDTO(userName, password);
		ChatParticipantDTO validateLogin = chatUserService.validateLogin(createChatUserLoginDTO);

		if (validateLogin != null) {
			logger.log(Level.SEVERE, "authentication granted");
			UsernamePasswordAuthenticationToken result = createToken(authentication, authority);
			return result;
		} else {
			logger.log(Level.SEVERE, "authentication not granted");
			return null;
		}

	}

	private UsernamePasswordAuthenticationToken createToken(Authentication authentication, String authority) {
		logger.log(Level.SEVERE, "createToken " + authentication.getPrincipal() + " " + authentication.getCredentials()
				+ " " + authority);

		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(authority));
		final UserDetails userDetails = new User((String) authentication.getPrincipal(),
				(String) authentication.getCredentials(), authorities);

		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		if (authentication == UsernamePasswordAuthenticationToken.class) {
			return true;
		} else {
			return false;
		}
	}

}
