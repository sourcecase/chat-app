package com.github.sourcecase.chat.web.security.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ChatWebAppAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(ChatWebAppAuthenticationProvider.class.getName());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.log(Level.SEVERE, "authenticate");

		final String userName = authentication.getName();
		if ((userName == null) || userName.equals("")) {
			logger.log(Level.SEVERE, "user name was empty");
			return null;
		}

		String authority = "USER";
		if (userName.equals("Chris")) {
			logger.info("Chris is welcome.");
		} else if (userName.equals("admin")) {
			logger.info("admin is welcome.");
			authority = "ADMIN";
		} else {
			logger.info("not allowed so far");
			return null;
		}

		// final ChatParticipantDTO chatParticipantDTO =
		// userService.findByUserName(userName);
		// if (userDetailsDTO == null) {
		// logger.log(Level.SEVERE, "username '{}' not found", userName);
		// return null;
		// }
		//
		// final String crendentials =
		// authentication.getCredentials().toString();
		// if (crendentials.equals(chatParticipantDTO.getPassword()) == false) {
		// logger.log(Level.SEVERE, "password mismatch");
		// return null;
		// }

		UsernamePasswordAuthenticationToken result = createToken(authentication, authority);
		return result;

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
