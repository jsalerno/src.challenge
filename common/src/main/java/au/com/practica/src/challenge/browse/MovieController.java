package au.com.practica.src.challenge.browse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.model.AbstractMovieRepresentation;
import au.com.practica.src.challenge.model.ActorModelAssembler;
import au.com.practica.src.challenge.model.MovieModelAssembler;
import au.com.practica.src.challenge.service.ActorReadService;
import au.com.practica.src.challenge.service.MovieReadService;

@RestController
@RequestMapping("/browse")
public class MovieController {
	@Autowired
	protected MovieReadService service;
	@Autowired
	protected ActorReadService actors;
	@Autowired
	protected ActorModelAssembler actorAssembler;
	@Autowired
	protected AbstractMovieRepresentation<?> actorRep;

	@GetMapping("/movies/{movieId}")
	public EntityModel<Movie> one(@PathVariable String movieId) {
		return actorRep.getAssembler().toModel(service.findById(movieId));
	}

	@GetMapping("/movies/paged")
	public ResponseEntity<PagedModel<EntityModel<Movie>>> paged(Pageable pageable) {
		Page<Movie> inventories = service.all(pageable);
		return ResponseEntity
			.ok()
			.contentType(MediaTypes.HAL_JSON)
			.body(actorRep.getPagedResourcesAssembler().toModel(inventories, actorRep.getAssembler()));
	}

	@PostMapping("/movies/matching")
	public CollectionModel<EntityModel<Movie>> byCriteria(@RequestBody(required = true) Movie example) {
		MovieModelAssembler assembler = actorRep.getAssembler();
		List<EntityModel<Movie>> movies = service.findByCriteria(example).stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(movies, linkTo(methodOn(MovieController.class).paged(Pageable.ofSize(10))).withSelfRel());
	}

	@GetMapping("/movies/{movieId}/actors")
	public ResponseEntity<CollectionModel<EntityModel<Actor>>> getAllActors(@PathVariable String movieId) {
		Actor actor = new Actor();
		actor.getMovies().add(service.findById(movieId));

		List<Actor> actorEntities = actors.findByCriteria(actor);
		return new ResponseEntity<>(
			actorAssembler.toCollectionModel(actorEntities),
			HttpStatus.OK);
	}
}