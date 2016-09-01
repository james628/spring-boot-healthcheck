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
	 * @return 체크하지 않음:null, DB오류:-1, 정상:응답시간(ms)
	 */
	@Override
	public Long checkDB(ApplicationContext context) {
		return null;
	}

	/**
	 * Check for Redis Connection
	 * @param context - For get Spring Bean in your application
	 * @return Don't use Redis: null, Error: -1, Success: response time(ms)
	 */
	@Override
	public Long checkRedis(ApplicationContext context) {
		return null;
	}

}
