package au.com.practica.src.challenge.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.update.MovieUpdateController;

@Component
public class EditMovieModelAssembler extends MovieModelAssembler {
	@Override
	public EntityModel<Movie> toModel(Movie mov) {
		EntityModel<Movie> of = super.toModel(mov);

		of.add(linkTo(methodOn(MovieUpdateController.class).add(mov)).withRel("add"));
		of.add(linkTo(methodOn(MovieUpdateController.class).delete(mov.getMovieId())).withRel("delete"));
		of.add(linkTo(methodOn(MovieUpdateController.class).update(mov)).withRel("update"));

		of.add(linkTo(methodOn(MovieUpdateController.class).addActor(mov.getMovieId(), null)).withRel("addActor"));
		of.add(linkTo(methodOn(MovieUpdateController.class).removeActor(mov.getMovieId(), null)).withRel("removeActor"));
		return of;
	}
}
