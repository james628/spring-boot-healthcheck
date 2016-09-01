package com.studio4365.spring.healthcheck;

import org.springframework.context.ApplicationContext;

public interface HealthCheckInterface {
	
	/**
	 * Check for Database Connection
	 * @param context - For get Spring Bean in your application
	 * @return 
	 * 		Success: response time(ms)
	 * 		Error: -1
	 * 		Don't use DB: null
	 */
	abstract public Long checkDB(ApplicationContext context);

	/**
	 * Check for Redis Connection
	 * @param context - For get Spring Bean in your application
	 * @return 
	 * 		Success: response time(ms)
	 * 		Error: -1
	 * 		Don't use Redis: null
	 */
	abstract public Long checkRedis(ApplicationContext context);
	
	abstract public String version();

}
