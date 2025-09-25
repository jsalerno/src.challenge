package au.com.practica.src.challenge.bean.condition;

public class BrowseEntityActionsCondition extends EntityActionsCondition {
	@Override
	protected boolean matches(String role) {
		return "browse".equals(role);
	}
}
