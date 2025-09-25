package au.com.practica.src.challenge.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.model.EditMovieModelAssembler;
import au.com.practica.src.challenge.service.ManagementService;

@RestController
@RequestMapping("/manage")
public class MovieUpdateController {
	@Autowired
	protected EditMovieModelAssembler editAssembler;

	@Autowired
	private ManagementService mgt;

	@PostMapping("/movies/add")
	public EntityModel<Movie> add(@RequestBody(required = true) Movie example) {
		return editAssembler.toModel(mgt.addMovie(example));
	}

	@DeleteMapping("/movies/delete/{movieId}")
	public EntityModel<Movie> delete(@PathVariable String movieId) {
		return editAssembler.toModel(mgt.deleteMovie(movieId));
	}

	@PatchMapping("/movies/patchAttributes")
	public EntityModel<Movie> update(@RequestBody(required = true) Movie update) {
		return editAssembler.toModel(mgt.updateMovie(update));
	}

	@PatchMapping("/movies/{movieId}/addActor/{actorId}")
	public EntityModel<Movie> addActor(@PathVariable String movieId, @PathVariable Long actorId) {
		return editAssembler.toModel(mgt.addActorToMovie(actorId, movieId));
	}

	@PatchMapping("/movies/{movieId}/removeActor/{actorId}")
	public EntityModel<Movie> removeActor(@PathVariable String movieId, @PathVariable Long actorId) {
		return editAssembler.toModel(mgt.removeActorFromMovie(actorId, movieId));
	}
}
