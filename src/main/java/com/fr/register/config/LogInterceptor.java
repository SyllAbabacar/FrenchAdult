package com.fr.register.config;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class LogInterceptor implements HandlerInterceptor {
 
    Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	 Instant start = Instant.now();
        Map<String, Object> inputMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
 
        logger.info("Incoming request is " + inputMap);
        logger.info("Incoming url is: "+ request.getRequestURL());
        Instant finish = Instant.now();
        long time = Duration.between(start, finish).toMillis();
        logger.info("time request ms :" +  time);
        return true;
    }
 
 
 
     
}