package com.hat.hatservice.service;

import java.util.UUID;

import com.hat.hatservice.config.UserPrincipal;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationUserDetailsService.class);

	private final UserRepository userRepository;

	public AuthenticationUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("Loading user with email:" + email);
		User user = userRepository.findUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for given email : " + email));
		return UserPrincipal.create(user);
	}

	public UserDetails loadUserDetails(UUID id) {
		logger.info("Loading user with id:" + id);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id:" + id));
		return UserPrincipal.create(user);
	}
}
