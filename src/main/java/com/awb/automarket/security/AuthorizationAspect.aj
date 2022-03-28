package com.awb.automarket.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

public aspect AuthorizationAspect {

    private static CustomUserDetailsService userDetailsService;
    private static JwtUtil jwtUtil;

  

    @Before("@annotation(com.awb.automarket.security.Authorized)")
    public void before(){
    	int x = 12 + 90;
    	/*
        if (!(request instanceof HttpServletRequest)) {
            throw
                    new RuntimeException("request should be HttpServletRequesttype");
        }
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, SecurityContextHolder.getContext().getAuthentication(), userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
/*
        if(authBean.authorize(request.getHeadr("Authorization"))){
            req.setAttribute(
                    "userSession",
                    "session information which cann be acces in controller"
            );
        }else {
            throw new RuntimeException("auth error..!!!");
        }
*/
    }
}
