package au.com.practica.src.challenge.bean;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ActorAttributes {
	private @Nonnull Long actorId;
	private @NonNull String firstName;
	private @NonNull String lastName;
	private @NonNull Long dob;
}
