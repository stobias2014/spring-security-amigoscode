package com.tobias.saul.springsecuritydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//csrf automatically enabled with Spring Security
//by default in memory database is used for session id

@Configuration
@EnableWebSecurity
//allows for annotation based config 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//order of definition for matchers matters
		
		//authorize any request made by client with basic auth
		http
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//			.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRAINEE.name())
			.anyRequest()
			.authenticated()
			.and()
			//.httpBasic()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/courses", true)
			.and()
			.rememberMe(); //defaults to 2 weeks
	}
	
	
	//retrieves user from a database
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails sTobiasUser = User.builder()
			.username("stobias")
			.password(passwordEncoder.encode("password"))
//			.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT
			.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
			.build();
		
		UserDetails kTobiasUser = User.builder()
				.username("ktobias")
				.password(passwordEncoder.encode("123"))
//				.roles(ApplicationUserRole.ADMIN.name()) //ROLE_ADMIN
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails tomUser = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("123"))
//				.roles(ApplicationUserRole.ADMIN_TRAINEE.name()) //ROLE_ADMIN_TRAINEE
				.authorities(ApplicationUserRole.ADMIN_TRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(
					sTobiasUser,
					kTobiasUser,
					tomUser
				);
	}
	
	
	
	
}
