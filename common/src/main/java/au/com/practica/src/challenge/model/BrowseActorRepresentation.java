package au.com.practica.src.challenge.model;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.condition.BrowseEntityActionsCondition;

@Component
@Conditional(BrowseEntityActionsCondition.class)
public class BrowseActorRepresentation extends AbstractActorRepresentation<ActorModelAssembler> {
}
