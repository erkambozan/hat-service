package com.hat.hatservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.hatservice.db.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final SecurityProperties securityProperties;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SecurityProperties securityProperties) {
		this.authenticationManager = authenticationManager;
		this.securityProperties = securityProperties;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		try {
			User creds = new ObjectMapper()
					.readValue(req.getInputStream(), User.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(),
							creds.getPassword(),
							new ArrayList<>())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException {
		String token = JWT.create()
				.withSubject(((User) auth.getPrincipal()).getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getAuth().getExpiration()))
				.sign(Algorithm.HMAC512(securityProperties.getAuth().getSecretKey().getBytes()));

		String body = ((User) auth.getPrincipal()).getEmail() + " " + token;

		res.getWriter().write(body);
		res.getWriter().flush();
	}
}