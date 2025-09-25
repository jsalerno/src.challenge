package au.com.practica.src.challenge.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.browse.MovieController;

@Component
@Primary
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {
	@Override
	public EntityModel<Movie> toModel(Movie movie) {
		EntityModel<Movie> of = EntityModel.of(movie,
			linkTo(methodOn(MovieController.class).one(movie.getId())).withSelfRel(),
			linkTo(methodOn(MovieController.class).paged(Pageable.ofSize(10))).withRel("movies"));

		of.add(linkTo(methodOn(MovieController.class).getAllActors(movie.getMovieId())).withRel("actors"));
		return of;
	}
}
