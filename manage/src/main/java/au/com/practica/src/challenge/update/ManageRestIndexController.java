package au.com.practica.src.challenge.update;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.practica.src.challenge.browse.Indices;

@RestController
@RequestMapping("/browse")
public class ManageRestIndexController {
	@GetMapping
	public RepresentationModel<?> index() {
		RepresentationModel<?> rootModel = Indices.indexImpl();
		return rootModel;
	}
}
