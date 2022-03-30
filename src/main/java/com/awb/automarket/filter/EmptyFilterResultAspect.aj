package com.awb.automarket.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.awb.automarket.dto.ServiceResponseModel;

@Component
@Aspect
public aspect EmptyFilterResultAspect {
		@AfterReturning(
	        pointcut = "execution(* com.awb.automarket.controller.AdvertContoller.GetAllAdverts(..))", 
	        returning = "resp")
	    public void uploadAfterReturning(JoinPoint joinPoint, ServiceResponseModel resp ) {
	    	if(resp.responseData == null) {
	    		System.out.println("Filtrul nu a intors rezultate. Filtru = " + joinPoint.getArgs()[0] + " | Page = " + joinPoint.getArgs()[1]);	            
	    	}
	    }
}
