package com.awb.automarket.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.advertDto.AdvertListResponse;

@Component
@Aspect
public aspect EmptyFilterResultAspect {
		@AfterReturning(
	        pointcut = "execution(* com.awb.automarket.controller.AdvertController.GetAllAdverts(..)) ", 
	        returning = "resp")
	    public void uploadAfterReturning(ResponseEntity resp, JoinPoint joinPoint ) {
    		System.out.println("A intrat pe AfterReturning filtered results!");
    		try {
				AdvertListResponse respData = (AdvertListResponse) resp.getBody();
				if (respData == null || respData.totalCount == 0) {
					System.out.println("Filtrul nu a intors rezultate. Filtru = " + joinPoint.getArgs()[0] + " | Page = " + joinPoint.getArgs()[1]);	            
				} 
			} catch (Exception ex) {
	    		System.out.println("Nu s-a putut realiza conversia pentru AdvertListResponse!");
			}
    		
	    }
}
