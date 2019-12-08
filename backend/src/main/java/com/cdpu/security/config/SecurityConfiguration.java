package com.cdpu.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;  
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;  

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		 
	  
	  @Override  
	  protected void configure(HttpSecurity http) throws Exception {  
	        http.csrf().disable().cors().and().authorizeRequests()  
	                .antMatchers(HttpMethod.POST, "/login").permitAll();  
	                  
	                
	    } 
	
	  @Bean  
	  CorsConfigurationSource corsConfigurationSource() {  
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
	        CorsConfiguration config = new CorsConfiguration();  
	        config.setAllowedOrigins(Arrays.asList("*"));  
	        config.setAllowedMethods(Arrays.asList("*"));  
	        config.setAllowedHeaders(Arrays.asList("*"));  
	        config.setAllowCredentials(true);  
	        config.applyPermitDefaultValues();  
	  
	        source.registerCorsConfiguration("/**", config);  
	        return source;  
	    }
	  
	
}
