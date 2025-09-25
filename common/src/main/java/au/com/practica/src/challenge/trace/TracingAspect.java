package au.com.practica.src.challenge.trace;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class TracingAspect {
	public static final Map<String, AtomicInteger> counters = new HashMap<>();

	@Before(value = "execution(public * au.com.practica.src.challenge.*.*Controller.*(..))")
	public void trapRestCall(JoinPoint jp) throws Exception {
		Signature signature = jp.getSignature();
		Object target = jp.getTarget();
		String name = signature.getName();
		String klazz = target.getClass().getSimpleName();

		final String key = klazz + "." + name;
		int count = counters.computeIfAbsent(key, n -> new AtomicInteger()).incrementAndGet();
		log.info("call:{} count:{}", key, count);
	}
}
