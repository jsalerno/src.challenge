package au.com.practica.src.challenge.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.bean.ActorAttributes;
import au.com.practica.src.challenge.model.EditActorModelAssembler;
import au.com.practica.src.challenge.service.ManagementService;

@RestController
@RequestMapping("/manage")
public class ActorUpdateController {
	@Autowired
	protected EditActorModelAssembler editAssembler;

	@Autowired
	private ManagementService mgt;

	@PostMapping("/actors/add")
	public EntityModel<Actor> add(@RequestBody(required = true) ActorAttributes example) {
		return editAssembler.toModel(mgt.addActor(example));
	}

	@DeleteMapping("/actors/delete/{actorId}")
	public EntityModel<Actor> delete(@PathVariable Long actorId) {
		return editAssembler.toModel(mgt.deleteActor(actorId));
	}

	@PatchMapping(value = "/actors/patchAttributes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<Actor> update(@RequestBody(required = true) ActorAttributes update) {
		return editAssembler.toModel(mgt.updateActor(update));
	}
}
