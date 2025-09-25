package au.com.practica.src.challenge.bean.condition;

public class ManageEntityActionsCondition extends EntityActionsCondition {
	@Override
	protected boolean matches(String role) {
		return "manage".equals(role);
	}
}
