package au.com.practica.src.challenge.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class MovieAttributes {
	private @NonNull String movieId;
	private @NonNull String name;
	private @NonNull String description;
	private @NonNull Integer releaseYear;
	private List<String> resources = new ArrayList<>();
}
