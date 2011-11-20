package com.sno.spring.test.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

@Aspect
public class BaseEntityAnnotationSupport {
	
	public interface ITest {
		String test1();
	}
	
	public static class Test implements ITest {
		public String test1() {
			return "hello";
		}		
	}

	@DeclareMixin("com.sno.spring.test.domain.Contact")
    public static ITest createMoodyImplementation() {
      return new Test();
    }
}
