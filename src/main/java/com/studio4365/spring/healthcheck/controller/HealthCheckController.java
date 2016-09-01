package com.studio4365.spring.healthcheck.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studio4365.spring.healthcheck.exception.HealthCheckException;

@Controller
public class HealthCheckController {
	
	@Value("${healthCheckClassFullName}")
	private String healthCheckClassFullName;
	@Autowired
	private ApplicationContext applicationContext;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@RequestMapping(value="healthcheck", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> healthCheck() {

		Map<String, Object> result = new HashMap<>();
		result.put("timestamp", System.currentTimeMillis());
		result.put("return_code", 0);
		result.put("return_message", "success");
		
		Class healthCheckClass = null;
		Object obj = null;
		try {
			healthCheckClass = Class.forName(healthCheckClassFullName);
			obj = healthCheckClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("return_code", -1);
			result.put("return_message", e.getMessage());
		}
		Long dbResponse = null;
		Long redisResponse = null;
		String version = null;
		try {
			Method checkDB = healthCheckClass.getMethod("checkDB", ApplicationContext.class);
			dbResponse = (Long)checkDB.invoke(obj, applicationContext);
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("return_code", -1);
			result.put("return_message", e.getMessage());
		}
		try {
			Method checkRedis = healthCheckClass.getMethod("checkRedis", ApplicationContext.class);
			redisResponse = (Long)checkRedis.invoke(obj, applicationContext);
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("return_code", -1);
			result.put("return_message", e.getMessage());
		}
		try {
			Method versionMethod = healthCheckClass.getMethod("version");
			version = (String)versionMethod.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> context = new HashMap<>();
		if (dbResponse != null) {
			context.put("db_response_time", dbResponse);
			if (dbResponse.longValue() == -1) {
				result.put("return_code", -1);
				result.put("return_message", "DB error");
			}
		}
		if (redisResponse != null) {
			context.put("redis_response_time", redisResponse);
			if (redisResponse.longValue() == -1) {
				result.put("return_code", -1);
				result.put("return_message", "Redis error");
			}
		}
		if (version != null) {
			result.put("version", version);
		}
		result.put("context", context);
		if (MapUtils.getIntValue(result, "return_code") != 0) {
			throw new HealthCheckException(result);
		}
		return result;
	}
	
	@ExceptionHandler(HealthCheckException.class)
	@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
	public @ResponseBody Map<String, Object> error(HttpServletRequest request, HealthCheckException e) {
		return e.getResponse();
	}
	
}
