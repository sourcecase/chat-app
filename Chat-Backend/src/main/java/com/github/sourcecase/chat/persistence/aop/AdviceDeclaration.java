package com.github.sourcecase.chat.persistence.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class AdviceDeclaration {

	@Before("com.github.sourcecase.chat.persistence.aop.PointCutDeclaration.anyStorage()")
	private void anyStorageBefore(JoinPoint jp) {
		String kind = jp.getKind();
		Logger.getLogger(AdviceDeclaration.class.getName()).log(Level.SEVERE,
				"anyStorageBefore advice called with kind:" + kind);
	}
}
