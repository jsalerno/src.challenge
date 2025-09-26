package au.com.practica.src.challenge.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActorAttributes {
	private Long actorId;
	private @NonNull String firstName;
	private @NonNull String lastName;
	private @NonNull Long dob;
}
