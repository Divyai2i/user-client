package com.userModule;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

 /* @Pointcut("execution(* com.userModule.user.controller.UserController.*(..))")
    private void commandLineRunner() {}*/

    @Around("@annotation(MethodExecutionTime)")
    //@Around("execution(* commandLineRunner(..))")
    //@Around("commandLineRunner()")
    //@Around(value = "execution(* com.userModule.user.controller.UserController.*(..))")
    public Object methodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.printf("%s executed in %sms %s",
            joinPoint.getSignature(), executionTime, System.lineSeparator());

        return proceed;
    }
}
