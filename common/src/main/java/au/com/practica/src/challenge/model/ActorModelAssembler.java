package au.com.practica.src.challenge.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.browse.ActorController;

@Component
@Primary
public class ActorModelAssembler implements RepresentationModelAssembler<Actor, EntityModel<Actor>> {
	@Override
	public EntityModel<Actor> toModel(Actor actor) {
		EntityModel<Actor> of = EntityModel.of(actor,
			linkTo(methodOn(ActorController.class).one(actor.getId())).withSelfRel(),
			linkTo(methodOn(ActorController.class).paged(Pageable.ofSize(10))).withRel("actors"));

		of.add(linkTo(methodOn(ActorController.class).getAllMovies(actor.getActorId())).withRel("movies"));
		return of;
	}
}
