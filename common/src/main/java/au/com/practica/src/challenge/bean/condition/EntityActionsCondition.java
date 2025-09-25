package au.com.practica.src.challenge.bean.condition;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class EntityActionsCondition implements Condition {
	static Properties APP_PROPS = new Properties();

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (APP_PROPS.isEmpty()) {
			loadProperties(context);
		}
		return matches(APP_PROPS.getProperty("app.role"));
	}

	protected abstract boolean matches(String object);

	private void loadProperties(ConditionContext context) {
		try {
			APP_PROPS.load(context.getResourceLoader().getResource("classpath:application.properties").getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
