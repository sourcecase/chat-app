package com.github.sourcecase.chat.persistence.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutDeclaration {

	@Pointcut("execution(* store*(..))")// the pointcut expression
	public void anyStorage() {}
	
}
