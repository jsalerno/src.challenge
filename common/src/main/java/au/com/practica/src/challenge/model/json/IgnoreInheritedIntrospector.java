package au.com.practica.src.challenge.model.json;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasIgnoreMarker(final AnnotatedMember m) {
		return m.getDeclaringClass() == RepresentationModel.class || super.hasIgnoreMarker(m);
	}
}