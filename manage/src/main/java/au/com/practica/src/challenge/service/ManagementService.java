package au.com.practica.src.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.bean.Movie;
import lombok.NonNull;

@Component
public class ManagementService {
	@Autowired
	private ActorReadService actorService;
	@Autowired
	private MovieReadService movieService;

	@Autowired
	private ActorUpdateService actorUpdateService;
	@Autowired
	private MovieUpdateService movieUpdateService;

	public Actor addActor(@NonNull Actor item) {
		return actorUpdateService.create(item);
	}

	public Actor updateActor(@NonNull Actor item) {
		return Optional.ofNullable(actorService.findById(item)).map(a -> {
			item.setMovies(a.getMovies());
			return actorUpdateService.update(item);
		}).orElseThrow(() -> new RuntimeException("Actor with Id '" + item.getId() + " does not exist"));
	}

	@Transactional
	public Movie addActorToMovie(@NonNull Long actorId, @NonNull String movieId) {
		Pair<Movie, Actor> movieActor = movieActor(actorId, movieId);
		movieActor.getFirst().getActors().add(movieActor.getSecond());
		return movieUpdateService.update(movieActor.getFirst());
	}

	@Transactional
	public Movie removeActorFromMovie(@NonNull Long actorId, @NonNull String movieId) {
		Pair<Movie, Actor> movieActor = movieActor(actorId, movieId);

		if (!movieActor.getFirst().getActors().contains(movieActor.getSecond())) {
			new RuntimeException("Actor with Id '" + actorId + " does not star in Movie with id " + movieId);
		}
		movieActor.getFirst().getActors().remove(movieActor.getSecond());
		return movieUpdateService.update(movieActor.getFirst());
	}

	private Pair<Movie, Actor> movieActor(Long actorId, String movieId) {
		Actor actor = Optional.ofNullable(actorService.findById(actorId)).map(a -> a)
			.orElseThrow(() -> new RuntimeException("Actor with Id '" + actorId + " does not exist"));
		Movie movie = Optional.ofNullable(movieService.findById(movieId)).map(a -> a)
			.orElseThrow(() -> new RuntimeException("Movie with Id '" + movieId + " does not exist"));

		return Pair.of(movie, actor);
	}

	public Actor deleteActor(@NonNull Long id) {
		Actor byId = actorService.findById(id);
		if (byId == null) {
			throw new RuntimeException();
		}

		Movie includesThisActor = new Movie();
		// includesThisActor.getActors().add(id);
		includesThisActor.getActors().add(byId);
		List<Movie> containsThisActor = movieService.findByCriteria(includesThisActor);
		if (!containsThisActor.isEmpty()) {
			throw new RuntimeException();
		}

		actorUpdateService.delete(byId);
		return byId;
		// TODO Auto-generated method stub

	}

	public Movie addMovie(@NonNull Movie item) {
		return movieUpdateService.create(item);
	}

	public Movie deleteMovie(@NonNull String id) {
		Movie byId = movieService.findById(id);
		if (byId == null) {
			throw new RuntimeException();
		}

		// Movie includesThisActor = new Movie();
		// // includesThisActor.getActors().add(id);
		// includesThisActor.getActors().add(byId);
		// List<Movie> containsThisActor = movieService.findByCriteria(includesThisActor);
		// if (!containsThisActor.isEmpty()) {
		// throw new RuntimeException();
		// }

		movieUpdateService.delete(byId);
		return byId;
		// TODO Auto-generated method stub

	}

	public Movie updateMovie(@NonNull Movie item) {
		return Optional.ofNullable(movieService.findById(item)).map(a -> {
			// item.setMovies(a.getMovies());
			return movieUpdateService.update(item);
		}).orElseThrow(() -> new RuntimeException("Movie with Id '" + item.getId() + " does not exist"));
	}
}
