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
import au.com.practica.src.challenge.model.AbstractActorRepresentation;
import au.com.practica.src.challenge.model.ActorModelAssembler;
import au.com.practica.src.challenge.model.MovieModelAssembler;
import au.com.practica.src.challenge.service.ActorReadService;
import au.com.practica.src.challenge.service.MovieReadService;

@RestController
@RequestMapping("/browse")
public class ActorController {
	@Autowired
	protected ActorReadService service;
	@Autowired
	protected MovieReadService movies;
	@Autowired
	protected MovieModelAssembler movieAssembler;
	@Autowired
	protected AbstractActorRepresentation<?> actorRep;

	@GetMapping("/actors/{actorId}")
	public EntityModel<Actor> one(@PathVariable Long actorId) {
		return actorRep.getAssembler().toModel(service.findById(actorId));
	}

	@GetMapping("/actors/paged")
	public ResponseEntity<PagedModel<EntityModel<Actor>>> paged(Pageable pageable) {
		Page<Actor> inventories = service.all(pageable);
		return ResponseEntity
			.ok()
			.contentType(MediaTypes.HAL_JSON)
			.body(actorRep.getPagedResourcesAssembler().toModel(inventories, actorRep.getAssembler()));
	}

	@PostMapping("/actors/matching")
	public CollectionModel<EntityModel<Actor>> byCriteria(@RequestBody(required = true) Actor example) {
		ActorModelAssembler assembler = actorRep.getAssembler();
		List<EntityModel<Actor>> actors = service.findByCriteria(example).stream() //
			.map(assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(actors, linkTo(methodOn(ActorController.class).paged(Pageable.ofSize(10))).withSelfRel());
	}

	@GetMapping("/actors/{actorId}/movies")
	public ResponseEntity<CollectionModel<EntityModel<Movie>>> getAllMovies(@PathVariable Long actorId) {
		Movie m = new Movie();
		m.getActors().add(service.findById(actorId));

		List<Movie> movieEntities = movies.findByCriteria(m);
		return new ResponseEntity<>(
			movieAssembler.toCollectionModel(movieEntities),
			HttpStatus.OK);
	}
}