package com.hat.hatservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hat.security")
public class SecurityProperties {
	private final Auth auth = new Auth();

	public static class Auth {
		private String secretKey;
		private long expiration;

		public String getSecretKey() {
			return secretKey;
		}

		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}

		public long getExpiration() {
			return expiration;
		}

		public void setExpiration(long expiration) {
			this.expiration = expiration;
		}
	}

	public Auth getAuth() {
		return auth;
	}
}

