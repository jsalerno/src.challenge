package au.com.practica.src.challenge.security;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
	private final AuthenticationManager authenticationManager;
	private final JwtEncoder jwtEncoder;

	public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtEncoder = jwtEncoder;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, String> login(
		@RequestParam MultiValueMap<String, String> loginRequest) {
		String username = loginRequest.get("username").get(0);
		String password = loginRequest.get("password").get(0);

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Instant now = Instant.now();
		String token = jwtEncoder.encode(JwtEncoderParameters.from(
			org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
				.subject(username)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(3600)) // 1-hour expiration
				.claim("scope", "read write")
				.build()))
			.getTokenValue();

		return Map.of("access_token", token);
	}
}
