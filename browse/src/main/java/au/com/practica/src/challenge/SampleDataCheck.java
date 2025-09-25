package au.com.practica.src.challenge;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.repo.ActorRepository;
import au.com.practica.src.challenge.repo.MovieRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Component
@DependsOn("h2Server")
@Log4j2
public class SampleDataCheck {
	@Autowired
	ActorRepository actors;
	@Autowired
	MovieRepository movies;

	@PostConstruct
	protected void initialize() throws ParseException {
		log.info("Sample Data Check ...");
		actors.findAll().forEach(a -> log.info("Created Actor:" + a));
		movies.findAll().forEach(m -> log.info("Created Movie: " + m));
	}

}
