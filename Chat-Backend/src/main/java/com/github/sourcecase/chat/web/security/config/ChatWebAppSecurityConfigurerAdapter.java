package com.github.sourcecase.chat.web.security.config;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.web.config.ChatPathConfiguration;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationEntryPoint;
import com.github.sourcecase.chat.web.security.authentication.ChatAuthenticationProcessingFilter;
import com.github.sourcecase.chat.web.security.authentication.ChatWebAppAuthenticationProvider;
import com.github.sourcecase.chat.web.security.logout.ChatLogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
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

		http.csrf().disable();
		this.createChatAuthenticationProcessingFilter();
		http.addFilter(new ChatLogoutFilter());

		http.authorizeRequests()
				.antMatchers(ChatPathConfiguration.LOGIN_URL)
				.permitAll()
				.antMatchers(ChatPathConfiguration.LOGIN_VALIDATE_URL)
				.permitAll()
				.antMatchers(ChatPathConfiguration.CHAT_WEB_SOCKET)
				.permitAll()
				.antMatchers( "/**")
				.permitAll();

		http.formLogin().loginPage(ChatPathConfiguration.LOGIN_URL);
		http.formLogin().failureUrl(ChatPathConfiguration.LOGIN_URL + "?error");
		http.formLogin().loginProcessingUrl(ChatPathConfiguration.LOGIN_VALIDATE_URL);
		http.formLogin().defaultSuccessUrl(ChatPathConfiguration.REST_CHAT_GROUPS);
		http.formLogin().permitAll();

		http.logout().permitAll();
		http.logout().logoutUrl(ChatPathConfiguration.LOGOUT_PERFORM_URL);

		http.exceptionHandling().authenticationEntryPoint(new ChatAuthenticationEntryPoint());

	}

	private void createChatAuthenticationProcessingFilter() throws Exception {

		if (http != null && chatDTOFactory != null) {
			if (chatAuthenticationProcessingFilter == null) {
				this.chatAuthenticationProcessingFilter = new ChatAuthenticationProcessingFilter(
                        this.authenticationManager(), chatDTOFactory);
				http.addFilter(this.chatAuthenticationProcessingFilter);
				logger.log(Level.SEVERE, "createChatAuthenticationProcessingFilter");
			}
		}

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/javascript/**", "/favicon.ico");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		logger.info("authenticationManagerBuilder injected");
		final ChatWebAppAuthenticationProvider chatWebAppAuthenticationProvider = new ChatWebAppAuthenticationProvider();
		beanFactory.autowireBean(chatWebAppAuthenticationProvider);
		authenticationManagerBuilder.authenticationProvider(chatWebAppAuthenticationProvider);
	}

}
