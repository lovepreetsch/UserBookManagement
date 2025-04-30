package com.bookManagement.net.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers(
						/* user Api's */ "/v1/user/signup", "/v1/user/login", "/v1/user/password/reset", "/v1/user/details", "/v1/user/update", "/v1/user/updateStatus",
						/* book Api's */ "/v1/book/add","/v1/book/list", "/v1/book/details", "/v1/book/update", "/v1/book/updateStatus","/v1/book/review", 
						/* customer Api's */ "/v1/customer/insert", "/v1/customer/list","/v1/customer/details", "/v1/customer/update", "/v1/customer/updateStatus","/v1/customer/order/history","/v1/customer/password/reset", 
						/* order Api's */ "/v1/order/createOrder", "/v1/order/review","/v1/order/details",
						/*Dashboard Api's*/"/v1/dashboard/list",
						/* swagger UI */ "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/swagger-resources/**", "/webjars/**", "/configuration/**")
				.permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
