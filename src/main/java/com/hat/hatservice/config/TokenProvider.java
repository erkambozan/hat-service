package com.hat.hatservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class TokenProvider {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String SIGN_UP_URL = "/hat/register";
	public static final String FORGOT_PASSWORD = "/hat/forgotpassword";
	public static final String LOGIN_URL = "/hat/authenticate";
	public static final String RESET_PASSWORD = "/hat/resetpassword";
	public static final String VALIDATE_TOKEN = "/hat/validatetoken";

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private final SecurityProperties securityProperties;

	@Autowired
	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;

	public TokenProvider(SecurityProperties securityProperties, HttpServletRequest httpServletRequest, UserRepository userRepository) {
		this.securityProperties = securityProperties;
		this.httpServletRequest = httpServletRequest;
		this.userRepository = userRepository;
	}

	public String createJWTToken(String username) {
		return createJWTToken(username, securityProperties.getAuth().getSecretKey(), securityProperties.getAuth().getExpiration());

	}

	public String createJWTToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return createJWTToken(userPrincipal.getUsername(), securityProperties.getAuth().getSecretKey(), securityProperties.getAuth().getExpiration());

	}

	public static String createJWTToken(String username, String securityKey, long expiration) {
		return JWT.create()
				.withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(HMAC512(securityKey.getBytes()));

	}

	public boolean isValidToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(securityProperties.getAuth().getSecretKey()).parseClaimsJws(authToken);
			return true;
		} catch (Throwable e) {
			logger.error("Failed to create JWT token. Error:", e);
		}
		return false;
	}

	public static String createHeaderToken(String token) {
		return TOKEN_PREFIX + token;
	}

	public static String parseJWTToken(String token, String secretKey) {
		return JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
				.build()
				.verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();
	}

	public Optional<User> getLoggedUser() {
		String token = httpServletRequest.getHeader(TokenProvider.AUTHORIZATION_HEADER);
		if (token != null) {
			String user = parseJWTToken(token, this.securityProperties.getAuth().getSecretKey());
			return userRepository.findUserByEmail(user);
		}
		return Optional.empty();
	}

}
