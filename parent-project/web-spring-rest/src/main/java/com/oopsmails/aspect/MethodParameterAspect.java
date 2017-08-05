package com.oopsmails.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Created by liu on 2017-07-22.
 */

@Aspect
@Component
public class MethodParameterAspect {
    @Before("execution(* com.oopsmails.controller.UserController.getAll(..))")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Integer[] ids = (Integer[])parameterValues[0];
        if (ids != null && ids[0] == 2) {
            ids[0] = 1;
        }

        System.out.println("============ logBefore() is running!");
        System.out.println("hijacked : " + joinPoint.getSignature().getName());
        System.out.println("******");
    }
}
