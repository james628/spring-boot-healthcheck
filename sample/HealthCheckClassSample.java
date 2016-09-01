import org.springframework.context.ApplicationContext;

import com.studio4365.spring.healthcheck.HealthCheckInterface;

public class HealthCheckClassSample implements HealthCheckInterface {

	private static final String VERSION = "0.1";
	
	@Override
	public String version() {
		return VERSION;
	}

	/**
	 * Check for Database Connection
	 * @param context - For get Spring Bean in your application
	 * @return 
	 * 		Success: response time(ms)
	 * 		Error: -1
	 * 		Don't use DB: null
	 */
	@Override
	public Long checkDB(ApplicationContext context) {
		return null;
	}

	/**
	 * Check for Redis Connection
	 * @param context - For get Spring Bean in your application
	 * @return 
	 * 		Success: response time(ms)
	 * 		Error: -1
	 * 		Don't use Redis: null
	 */
	@Override
	public Long checkRedis(ApplicationContext context) {
		return null;
	}

}
