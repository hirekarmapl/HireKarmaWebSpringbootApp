package com.hirekarma.config;

import java.util.concurrent.Executor;


import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.hirekarma.exception.AsyncException;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

	@Autowired
	private AsyncException asyncException;
	

	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Async-Thread");
		executor.initialize();
		return executor;
	}
//	@Override
//	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
////		return super.getAsyncUncaughtExceptionHandler();
//		return asyncException;
//	}
}
