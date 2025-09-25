package au.com.practica.src.challenge.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.update.ActorUpdateController;

@Component
public class EditActorModelAssembler extends ActorModelAssembler {
	@Override
	public EntityModel<Actor> toModel(Actor actor) {
		EntityModel<Actor> of = super.toModel(actor);
		of.add(linkTo(methodOn(ActorUpdateController.class).add(actor)).withRel("add"));
		of.add(linkTo(methodOn(ActorUpdateController.class).delete(actor.getActorId())).withRel("delete"));
		of.add(linkTo(methodOn(ActorUpdateController.class).update(actor)).withRel("update"));
		return of;
	}
}
