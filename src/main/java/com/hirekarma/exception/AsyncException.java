package com.hirekarma.exception;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class AsyncException implements AsyncUncaughtExceptionHandler{

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		System.out.println("Method Name : "+method.getName()+"\n"+java.util.Arrays.toString(params)+"\n\n"+"Error Mesage : "+ex.getMessage());
	}

}
