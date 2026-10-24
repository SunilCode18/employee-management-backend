package com.springboot.app.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.app.service.JwtService;
import com.springboot.app.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String authHeader = request.getHeader("Authorization");
//		String token = "";
//		String username = "";
//		
//		if(authHeader != null && authHeader.startsWith("Bearer ")) {
//			token = authHeader.substring(7);
//			username = jwtService.extractUsername(token);
//
//		}
//		
//		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails details = userDetailsService.loadUserByUsername(username);
//			
//			if(jwtService.validateToken(token, details)) {
//				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
//				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authtoken);
//			}
//		}
//		
//		filterChain.doFilter(request, response);
//			
//	}
	
	

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {

        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            if (username != null
                    && !username.isBlank()
                    && SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                UserDetails details =
                        userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(token, details)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    details,
                                    null,
                                    details.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT token");
            return;
        }
    }

    filterChain.doFilter(request, response);
}

	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String path = request.getServletPath();

	    System.out.println("JWT Filter checking path: " + path);
//	    return path.equals("/register") || path.equals("/login");
	    boolean skip = path.equals("/register") || path.equals("/login");

	    System.out.println("JWT Filter skip: " + skip);

	    return skip;

	}

}
