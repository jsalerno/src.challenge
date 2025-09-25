package au.com.practica.src.challenge.model.hateoas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Data
public class RestCollectionQueryResult {
	@JsonProperty("_links")
	private Links links;

	@JsonProperty("_embedded")
	private Map<String, List<Map<String, Object>>> embedded;

	@JsonIgnore
	private Map<String, Object> unknown = new HashMap<>();

	@JsonAnySetter
	public void add(String key, Object value) {
		unknown.put(key, value);
	}
}