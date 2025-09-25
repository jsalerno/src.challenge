package au.com.practica.src.challenge;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoOpApplication {
	public static void main(String... args) {
		throw new RuntimeException();
	}
}
