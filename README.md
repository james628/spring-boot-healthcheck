# spring-boot-healthcheck
 Generate REST API "/healthcheck" in your spring boot web project.
 
 "/healthcheck" provide many things.

1. Server status
2. Database status
3. Redis status
4. Current application version

EX)

	REQUEST (GET) - http://yourdomain.com/healthcheck
	
	RESPONSE - Content-Type: application/json
		{
			"return_message":"success",
			"context":{
				"db_response_time":2,
				"redis_response_time":1
			},
			"return_code":0,
			"version":"1.10",
			"timestamp":1472708562590
		}


## Getting started
1. import library into your spring boot project
	* maven
	```
	<dependency>
  		<groupId>com.studio4365</groupId>
  		<artifactId>spring-boot-healthcheck</artifactId>
  		<version>1.00</version>
	</dependency>
	```
	* gradle
	```
	compile('com.studio4365:spring-boot-healthcheck:1.00')
	```

2. Create your **healthCheckClass** java file with implements HealthCheckInterface
	
	ex) [HealthCheckClassSample.java](sample/HealthCheckClassSample.java)
	```
	import com.studio4365.spring.healthcheck.HealthCheckInterface;
	
	public class HealthCheckClass implements HealthCheckInterface {
		@Override
		...
	}
	```

3. Add **healthCheckClassFullName** into your application.properties or other Environment properties
healthCheckClassFullName=com.studio4365.spring.healthcheck.HealthCheckClass

4. Add basePackage **"com.studio4365.spring.healthcheck"** in your Component-scan setting
	* Annotation driven
	```
	@ComponentScan(basePackages={"com.yourdomain", "com.studio4365.spring.healthcheck"})
	```
	* XML driven
	```
	<context:component-scan base-package="com.yourdomain, com.studio4365.spring.healthcheck" />
	```

## Practice
1. AWS Elastic load balancer(ELB) Health Check

	**Ping Target** : HTTP:8080/healthcheck
	
	ELB default ping protocol TCP just check open port. It doesn't know about status of your application.
	If you use HTTP with "/healthcheck", you can know server status, database status, redis status and version after deploy.
