package au.com.practica.src.challenge.model;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.condition.ManageEntityActionsCondition;

@Component
@Conditional(ManageEntityActionsCondition.class)
public class ManageActorRepresentation extends AbstractActorRepresentation<EditActorModelAssembler> {
}
