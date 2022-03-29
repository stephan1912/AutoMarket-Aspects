package com.awb.aspects;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.apache.juli.FileHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.awb.automarket.controller.AdvertController;


@Aspect
public class AOPConsoleLoggger {

	@Around("execution(* com.awb.automarket.controller..*(..))")
    public Object intercept(final ProceedingJoinPoint point) throws Throwable {
        final Method method
                = MethodSignature.class.cast(point.getSignature()).getMethod();
        String mName = method.getName();
        String cName = method.getDeclaringClass().getSimpleName();
        
        System.out.println("A fost apelata functia "+mName+" din clasa "+cName + "!");
        Object out = null;
        try {
            out = point.proceed();
        } catch (Throwable t) {
        	logExceptions(t,point);
        }
        return out;
    }
	
    private void logExceptions(Throwable t, final ProceedingJoinPoint point) {
        final Method method
                = MethodSignature.class.cast(point.getSignature()).getMethod();
        String mName = method.getName();
        String cName = method.getDeclaringClass().getSimpleName();
        Object[] params = point.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append("Exception caught for [ ");
        sb.append(cName);
        sb.append(".");
        sb.append(mName);
        sb.append(" ]");
        System.out.println(sb.toString());

    }
}