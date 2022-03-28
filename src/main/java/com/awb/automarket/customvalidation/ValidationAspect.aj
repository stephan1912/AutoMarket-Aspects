package com.awb.automarket.customvalidation;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.awb.automarket.dto.ServiceResponseModel;

import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {
	/*
	@Before("@annotation(com.awb.automarket.customvalidation.RequireValidation)")
    public void BeforeRequireValidation(JoinPoint joinPoint) throws Throwable{
		Object args = joinPoint.getArgs();
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(joinPoint.getArgs()[0]);
    	
	}*/
	
	@Around("@annotation(com.awb.automarket.customvalidation.RequireValidation)")
    public Object AroundRequireValidation(final ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("Enter @Around annotation for model validation!");
		Object args = joinPoint.getArgs();
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(joinPoint.getArgs()[0]);
        if(validationResult != null) {
    		System.out.println("Model is not valid, break the execution and return error!");
        	return validationResult.toResponseEntity();
        }
		System.out.println("Model is valid, proceed!");
        return joinPoint.proceed();
    	
	}
}
