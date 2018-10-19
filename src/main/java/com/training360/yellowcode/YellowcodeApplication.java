package com.training360.yellowcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableWebSecurity
public class YellowcodeApplication extends
		WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(YellowcodeApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "/js/**", "/css/**", "/api/**").permitAll()
				.antMatchers("/admin.html").hasRole("ADMIN")
				.and()
				.formLogin()
				.and()
				.logout();

	}
}