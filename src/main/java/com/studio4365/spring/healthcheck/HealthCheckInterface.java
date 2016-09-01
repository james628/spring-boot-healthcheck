package com.studio4365.spring.healthcheck;

import org.springframework.context.ApplicationContext;

public interface HealthCheckInterface {
	
	/**
	 * Check for Database Connection
	 * @param context - For get Spring Bean in your application
	 * @return 체크하지 않음:null, DB오류:-1, 정상:응답시간(ms)
	 */
	abstract public Long checkDB(ApplicationContext context);

	/**
	 * Check for Redis Connection
	 * @param context - For get Spring Bean in your application
	 * @return Don't use Redis: null, Error: -1, Success: response time(ms)
	 */
	abstract public Long checkRedis(ApplicationContext context);
	
	abstract public String version();

}
