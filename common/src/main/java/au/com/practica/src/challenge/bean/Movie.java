package au.com.practica.src.challenge.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * ID = IMDB id
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = { "resources", "actors" })
public class Movie extends RepresentationModel<Movie> implements Identifiable<String> {
	/* IMDB to be used as ID */
	private @Id @NonNull String movieId;
	private @NonNull String name;
	private @NonNull String description;
	private @NonNull Integer releaseYear;
	private List<String> resources = new ArrayList<>();

	public Movie(MovieAttributes attrs) {
		this(attrs.getMovieId(), attrs.getName(), attrs.getDescription(), attrs.getReleaseYear());
		this.setResources(attrs.getResources());
	}

	@JsonManagedReference
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Movie_Actors", joinColumns = { @JoinColumn(name = "movieId") }, inverseJoinColumns = { @JoinColumn(name = "actorId") })
	private Set<Actor> actors = new HashSet<>();

	@Override
	public String getId() {
		return movieId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Movie)) {
			return false;
		}
		Movie m = (Movie) o;
		return Objects.equals(this.movieId, m.movieId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.movieId);
	}

	public void addActor(Actor actor) {
		actors.add(actor);
	}

	public MovieAttributes toAttributes() {
		MovieAttributes attrs = new MovieAttributes(movieId, name, description, releaseYear);
		attrs.setResources(resources);
		return attrs;
	}
}
