package au.com.practica.src.challenge.browse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;

public class Indices {
	public static RepresentationModel<?> indexImpl() {
		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(linkTo(methodOn(ActorController.class).paged(Pageable.ofSize(10))).withRel("actors"));
		rootModel.add(linkTo(methodOn(MovieController.class).paged(Pageable.ofSize(10))).withRel("movies"));
		return rootModel;
	}
}
