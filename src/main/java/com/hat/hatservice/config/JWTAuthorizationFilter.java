package com.hat.hatservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hat.hatservice.service.AuthenticationUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hat.hatservice.config.TokenProvider.TOKEN_PREFIX;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final SecurityProperties securityProperties;
	private final AuthenticationUserDetailsService authenticationUserDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, SecurityProperties securityProperties, AuthenticationUserDetailsService authenticationUserDetailsService) {
		super(authManager);
		this.securityProperties = securityProperties;
		this.authenticationUserDetailsService = authenticationUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws IOException, ServletException {

		String header = req.getHeader(com.hat.hatservice.config.TokenProvider.AUTHORIZATION_HEADER);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	// Reads the JWT from the Authorization header, and then uses JWT to validate the token
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(TokenProvider.AUTHORIZATION_HEADER);

		if (token != null) {
			// parse the token.
			String user = JWT.require(Algorithm.HMAC512(securityProperties.getAuth().getSecretKey().getBytes()))
					.build()
					.verify(token.replace(TOKEN_PREFIX, ""))
					.getSubject();

			if (user != null) {
				// new arraylist means authorities
				String userName = TokenProvider.parseJWTToken(token, securityProperties.getAuth().getSecretKey());
				UserDetails userDetails = authenticationUserDetailsService.loadUserByUsername(userName);
				return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());

			}

			return null;
		}

		return null;
	}
}