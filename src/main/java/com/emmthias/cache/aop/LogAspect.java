package com.emmthias.cache.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Define facets and monitor all requests to access com.emmthias.cache.controller directory
     */
    @Pointcut("execution(* com.emmthias.cache.controller.*.*(..))")
    public void log() {
    }

    //Define the method to execute before entering the cut plane
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //Get HttpServletRequest object get related data in the request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();  //Get request object
        String url = request.getRequestURL().toString();   //Get the requested url
        String ip = request.getRemoteAddr();     //Get the ip address of the request object
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //Get the method name of the request object request
        Object[] args = joinPoint.getArgs();     //Get request parameters of request object
        String requestLog = requestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    private String requestLog(String url, String ip, String classMethod, Object[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", url);
        jsonObject.put("ip", ip);
        jsonObject.put("method", classMethod);
        jsonObject.put("args", Arrays.toString(args));

        return jsonObject.toString();
    }

    //Define how to complete execution in faceted access
    @After("log()")
    public void doAfter() {
        logger.info("------------doAfter-----------------------");
    }

    //Define the method to get the value returned by the accessed Controller and print it in the log
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        logger.info("Result : {}", result);
    }
}