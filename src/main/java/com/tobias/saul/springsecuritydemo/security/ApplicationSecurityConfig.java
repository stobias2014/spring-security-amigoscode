package com.tobias.saul.springsecuritydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//authorize any request made by client with basic auth
		http
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	
	//retrieves user from a database
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails sTobiasUser = User.builder()
			.username("stobias")
			.password(passwordEncoder.encode("password"))
			.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT
			.build();
		
		UserDetails kTobiasUser = User.builder()
				.username("ktobias")
				.password(passwordEncoder.encode("123"))
				.roles(ApplicationUserRole.ADMIN.name()) //ROLE_ADMIN
				.build();
		
		return new InMemoryUserDetailsManager(
					sTobiasUser,
					kTobiasUser
				);
	}
	
	
	
	
}
