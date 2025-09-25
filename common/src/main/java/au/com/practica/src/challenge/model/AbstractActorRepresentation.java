package au.com.practica.src.challenge.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;

import au.com.practica.src.challenge.bean.Actor;
import lombok.Getter;

@Getter
public abstract class AbstractActorRepresentation<T extends ActorModelAssembler> {
	@Autowired
	protected T assembler;

	@Autowired
	protected PagedResourcesAssembler<Actor> pagedResourcesAssembler;
}
