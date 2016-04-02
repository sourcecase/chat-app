package com.github.sourcecase.chat.web.security.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.web.ChatPathConfiguration;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationEntryPoint;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationProcessingFilter;
import com.github.sourcecase.chat.web.security.authentication.ChatWebAppAuthenticationProvider;
import com.github.sourcecase.chat.web.security.logout.ChatLogoutFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.github.sourcecase.chat")
public class ChatWebAppSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(ChatWebAppSecurityConfigurerAdapter.class.getName());
	private ChatDTOFactory chatDTOFactory = null;
	private HttpSecurity http = null;
	private ChatAuthenticationProcessingFilter chatAuthenticationProcessingFilter = null;

	private @Autowired AutowireCapableBeanFactory beanFactory;

	public ChatWebAppSecurityConfigurerAdapter() {
		logger.log(Level.SEVERE, "init constructor");
	}

	@Autowired
	@Required
	public void setChatDTOFactory(ChatDTOFactory chatDTOFactory) {
		this.chatDTOFactory = chatDTOFactory;
		try {
			this.createChatAuthenticationProcessingFilter();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.log(Level.SEVERE, "configure(HttpSecurity http)");
		this.http = http;
		this.createChatAuthenticationProcessingFilter();
		http.addFilter(new ChatLogoutFilter());

		http.authorizeRequests()
				.antMatchers(ChatPathConfiguration.CHAT_INDEX, ChatPathConfiguration.CHAT_TEST,
						ChatPathConfiguration.REST_REGISTER_PERFORM_URL, ChatPathConfiguration.LOGIN_VALIDATE_URL,
						ChatPathConfiguration.LOGIN_URL, "/chat/error", ChatPathConfiguration.LOGIN_URL + "/*",
						ChatPathConfiguration.CHAT_ROOT_DATA_PATH + "/**")
				.permitAll().anyRequest().authenticated();

		http.logout().permitAll();
		http.logout().logoutUrl(ChatPathConfiguration.LOGOUT_PERFORM_URL);

		http.exceptionHandling().authenticationEntryPoint(new ChatAuthenticationEntryPoint());

	}

	private void createChatAuthenticationProcessingFilter() throws Exception {

		if (http != null && chatDTOFactory != null) {
			if (chatAuthenticationProcessingFilter == null) {
				this.chatAuthenticationProcessingFilter = new ChatAuthenticationProcessingFilter(
						ChatPathConfiguration.LOGIN_VALIDATE_URL, this.authenticationManager(), chatDTOFactory);
				http.addFilter(this.chatAuthenticationProcessingFilter);
				logger.log(Level.SEVERE, "createChatAuthenticationProcessingFilter");
			}
		}

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/chat/javascript/**", "/favicon.ico");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		logger.info("authenticationManagerBuilder injected");
		final ChatWebAppAuthenticationProvider chatWebAppAuthenticationProvider = new ChatWebAppAuthenticationProvider();
		beanFactory.autowireBean(chatWebAppAuthenticationProvider);
		authenticationManagerBuilder.authenticationProvider(chatWebAppAuthenticationProvider);
	}

}
