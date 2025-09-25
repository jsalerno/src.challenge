package au.com.practica.src.challenge.bean;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * ASSUME each actor has unique combination (unrealistic in the real world, but to avoid Fingerprinting the Actor in this mini project, this is sufficient)
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = { "movies" })
public class Actor extends RepresentationModel<Actor> implements Identifiable<Long> {
	private @Id @GeneratedValue Long actorId;
	private @NonNull String firstName;
	private @NonNull String lastName;
	private @NonNull Long dob;

	@Override
	public Long getId() {
		return actorId;
	}

	@JsonBackReference
	@ManyToMany(mappedBy = "actors")
	private Set<Movie> movies = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Actor)) {
			return false;
		}
		Actor employee = (Actor) o;
		return Objects.equals(this.actorId, employee.actorId) &&
			Objects.equals(this.firstName, employee.firstName) &&
			Objects.equals(this.lastName, employee.lastName) &&
			Objects.equals(this.dob, employee.dob);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.actorId, this.firstName, this.lastName, this.dob);
	}
}
