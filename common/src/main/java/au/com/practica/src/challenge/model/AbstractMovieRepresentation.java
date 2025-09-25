package au.com.practica.src.challenge.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;

import au.com.practica.src.challenge.bean.Movie;
import lombok.Getter;

@Getter
public abstract class AbstractMovieRepresentation<T extends MovieModelAssembler> {
	@Autowired
	protected T assembler;

	@Autowired
	protected PagedResourcesAssembler<Movie> pagedResourcesAssembler;
}
