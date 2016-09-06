import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.studio4365.spring.healthcheck.HealthCheckInterface;

import net.bluehole.billing.dao.master.HealthCheckDao;

public class HealthCheckClassSample implements HealthCheckInterface {
	private static final String VERSION = "0.1";
	
	private HealthCheckDao healthCheckDao;
	private RedisTemplate<String, Boolean> operations;
	
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
		if (healthCheckDao == null) {
			// Prepared data access object for Healthcheck from Spring Boot Application
			healthCheckDao = (HealthCheckDao)context.getBean("healthCheckDao");
		}
		long tic = System.currentTimeMillis();
		int result = -1;
		try {
			// probably "SELECT 1"
			result = healthCheckDao.selectOne();
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		long toc = System.currentTimeMillis();
		return result == 1 ? toc - tic : -1L;
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
		if (operations == null) {
			// Prepared redis template for Healthcheck from Spring Boot Application
			operations = (RedisTemplate<String, Boolean>)context.getBean("redisTemplate");
		}
		long tic = System.currentTimeMillis();
		Boolean result = false;
		try {
			// set value "true" in "healthcheck" key
			operations.opsForValue().set("healthCheck", true);
			// get value from "healthcheck" key and assign at result variable
			result = operations.opsForValue().get("healthCheck");
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		long toc = System.currentTimeMillis();
		return result == true ? toc - tic : -1L;
	}

}
