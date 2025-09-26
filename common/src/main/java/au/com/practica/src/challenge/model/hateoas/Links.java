package au.com.practica.src.challenge.model.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/* Links class */
@Data
@JsonInclude(Include.NON_NULL)
public class Links {
	@JsonProperty("self")
	private HRef self;
	@JsonProperty("actors")
	private HRef actors;
	@JsonProperty("movies")
	private HRef movies;
	@JsonProperty("add")
	private HRef add;
	@JsonProperty("delete")
	private HRef delete;
	@JsonProperty("update")
	private HRef update;
	@JsonProperty("addActor")
	private HRef addActor;
	@JsonProperty("removeActor")
	private HRef removeActor;
}