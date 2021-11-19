package com.fr.register.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

@Component
@Aspect
public class UserLogsAspect {

	private static final Logger logger = LoggerFactory.getLogger(UserLogsAspect.class);

	@Pointcut("execution(public * com.fr.register.web..*.*(..))")
	public void pointCut() {

	}

	@Around("@annotation(com.fr.register.config.TrackingExecutionTime)")
	public Object executionTime(ProceedingJoinPoint point) throws Throwable {

		long startTime = System.currentTimeMillis();

		Object object = point.proceed();

		long endtime = System.currentTimeMillis();

		logger.info("execution time : " + (endtime - startTime) + "ms");

		return object;
	}

	@Before("pointCut()")
	public void beforeMethod(JoinPoint joinPoint) {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		if (null != servletRequestAttributes) {

			HttpServletRequest request = servletRequestAttributes.getRequest();

			// Get the parameter information to be printed String requestURI =
			String requestURI = request.getRequestURI();

			String method = request.getMethod();

			String remoteAddr = request.getRemoteAddr();

			String jsonString = JSON.toJSONString(joinPoint.getArgs());

			// Print information logger.info("---------- REQUEST --------");
			logger.info("request time: {}", new SimpleDateFormat("yyyy MM DD HH: mm: SS").format(new Date()));
			logger.info("remoteAddr: {} ", remoteAddr);
			logger.info("requestURI : {}", requestURI);
			logger.info("Controller : {}", joinPoint.getTarget().getClass());
			logger.info("method type: {}", method);
			logger.info("req paras: {}", jsonString);
		}

	}

	@After("pointCut()")
	public void doAfter() {
	}

	// Define the method to get the value returned by the accessed Controller and
	// print it in the log

	@AfterReturning(returning = "result", pointcut = "pointCut()")
	public void doAfterReturning(Object result) {
		logger.info("----------- RESPONSE ---------------");
		logger.info("Result : {}", result);
	}

}
