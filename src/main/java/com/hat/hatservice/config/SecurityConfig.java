package com.hat.hatservice.config;

import com.hat.hatservice.service.AuthenticationUserDetailsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Profile("!test")
@EnableWebSecurity
@EnableConfigurationProperties()
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationUserDetailsService authenticationUserDetailsService;
	private final SecurityProperties securityProperties;

	public SecurityConfig(AuthenticationUserDetailsService authenticationUserDetailsService, SecurityProperties securityProperties) {
		this.authenticationUserDetailsService = authenticationUserDetailsService;
		this.securityProperties = securityProperties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(authenticationManager(), securityProperties, authenticationUserDetailsService), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, TokenProvider.SIGN_UP_URL, TokenProvider.LOGIN_URL, TokenProvider.FORGOT_PASSWORD, TokenProvider.RESET_PASSWORD,
						TokenProvider.VALIDATE_TOKEN).permitAll()
				.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
				.antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), securityProperties), UsernamePasswordAuthenticationFilter.class)
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}