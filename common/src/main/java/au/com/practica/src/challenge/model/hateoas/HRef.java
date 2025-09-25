package au.com.practica.src.challenge.model.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/* HRef class */
@Data
@JsonInclude(Include.NON_NULL)
public class HRef {
	@JsonProperty("href")
	private String href;
}