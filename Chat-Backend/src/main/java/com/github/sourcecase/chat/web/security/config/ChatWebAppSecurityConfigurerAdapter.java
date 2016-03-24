package com.github.sourcecase.chat.web.security.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.github.sourcecase.chat.web.config.ChatPathConfiguration;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationEntryPoint;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationProcessingFilter;
import com.github.sourcecase.chat.web.security.authentication.ChatWebAppAuthenticationProvider;
import com.github.sourcecase.chat.web.security.logout.ChatLogoutFilter;

@Configuration
@EnableWebSecurity
public class ChatWebAppSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(ChatWebAppSecurityConfigurerAdapter.class.getName());

	public ChatWebAppSecurityConfigurerAdapter() {
		logger.log(Level.SEVERE, "init constructor");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilter(new ChatAuthenticationProcessingFilter(ChatPathConfiguration.LOGIN_VALIDATE_URL,
				this.authenticationManager()));

		http.addFilter(new ChatLogoutFilter());

		http.authorizeRequests()
				.antMatchers(ChatPathConfiguration.CHAT_INDEX, ChatPathConfiguration.LOGIN_VALIDATE_URL,
						ChatPathConfiguration.LOGIN_URL, "/chat/error", ChatPathConfiguration.LOGIN_URL + "/*")
				.permitAll().anyRequest().authenticated();

		http.logout().permitAll();
		http.logout().logoutUrl(ChatPathConfiguration.LOGOUT_PERFORM_URL);

		http.exceptionHandling().authenticationEntryPoint(new ChatAuthenticationEntryPoint());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/chat/javascript/**", "/favicon.ico");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		logger.info("authenticationManagerBuilder injected");
		authenticationManagerBuilder.authenticationProvider(new ChatWebAppAuthenticationProvider());
	}

}
