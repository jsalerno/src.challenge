package au.com.practica.src.challenge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.repo.ActorRepository;
import au.com.practica.src.challenge.repo.MovieRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class SampleData {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	@Autowired
	ActorRepository actors;
	@Autowired
	MovieRepository movies;

	@PostConstruct
	protected void initialize() throws ParseException {
		theGodFather(actors, movies);

		actors.findAll().forEach(a -> log.info("Created Actor:" + a));
		movies.findAll().forEach(m -> log.info("Created Movie: " + m));
	}

	protected void theGodFather(ActorRepository actors, MovieRepository movies) throws ParseException {
		long movieCount = movies.count();
		if (movieCount > 0) {
			log.info("DB already set up");
			return;
		}

		Optional<Movie> tt0068646 = movies.findById("tt0068646");
		if(tt0068646.isPresent()) {
			log.info("The Godfather already exists !");
		}
		
		Movie m = new Movie(
			"tt0068646",
			"The Godfather",
			"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
			1972);
		movies.save(m);


		m.getActors().add(actors.save(new Actor("Al", "Pacino", SampleData.sdf.parse("1940/05/25").getTime())));
		m.getActors().add(actors.save(new Actor("Marlon", "Brando", SampleData.sdf.parse("1924/04/03").getTime())));
		movies.save(m);
	}
}
