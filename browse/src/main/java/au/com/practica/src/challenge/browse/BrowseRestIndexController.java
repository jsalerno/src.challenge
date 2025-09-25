package au.com.practica.src.challenge.browse;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/browse")
public class BrowseRestIndexController {
	@GetMapping
	public RepresentationModel<?> index() {
		RepresentationModel<?> rootModel = Indices.indexImpl();
		return rootModel;
	}
}
