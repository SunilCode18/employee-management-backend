package com.springboot.app.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.springboot.app.service.MyUserDetailsService;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    private JwtFilter jwtFilter;


	@Bean
    public AuthenticationProvider authenticationProvider(
            MyUserDetailsService detailsService) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(detailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
        	.cors(Customizer.withDefaults());

        http.authorizeHttpRequests(request -> request
        	    .requestMatchers("/register", "/login").permitAll()

        	    .requestMatchers(
        	        "/addEmployee",
        	        "/updateEmployee/**",
        	        "/deleteEmployee/**",
        	        "/admin/**"
        	    ).hasRole("ADMIN")

        	    .requestMatchers(
        	        "/getAllEmployees",
        	        "/getEmployee/**",
        	        "/search/**"        	        
        	    ).hasAnyRole("ADMIN", "EMPLOYEE")

        	    .anyRequest().authenticated()
        	);

//      http.httpBasic(Customizer.withDefaults());
        http.httpBasic(basic -> basic.disable());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
    	return config.getAuthenticationManager();
    }
    


	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(
	        JwtFilter jwtFilter) {
	
	    FilterRegistrationBean<JwtFilter> registration =
	            new FilterRegistrationBean<>(jwtFilter);
	
	    registration.setEnabled(false);
	
	    return registration;
	}
	
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOrigins(
	        List.of("http://localhost:5173")
	    );

	    configuration.setAllowedMethods(
	        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
	    );

	    configuration.setAllowedHeaders(
	        List.of("Authorization", "Content-Type")
	    );

	    UrlBasedCorsConfigurationSource source =
	        new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}

}


