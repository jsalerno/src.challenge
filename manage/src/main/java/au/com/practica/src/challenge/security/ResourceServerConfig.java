package au.com.practica.src.challenge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {
	private final UserService userService;

	public ResourceServerConfig(@Lazy UserService userService) {
		this.userService = userService;
	}

	@Bean
	SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			/* Basic */
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/register", "/auth/login").permitAll()
				.requestMatchers("/browse/**").permitAll()
				// .requestMatchers("/manage/**").permitAll()
				.requestMatchers("/h2-console").permitAll()
				.anyRequest().authenticated())
			/* OAuth2 */
			.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	UserDetailsService userDetailsService() {
		return userService;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
