package com.example.shoppinglist.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Modifier;
import java.util.stream.IntStream;

import static com.example.shoppinglist.util.HelperClass.cleanString;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    // AOP expression for which methods shall be intercepted
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        // Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        // Log method execution time
        log.info("Execution time of {}.{} :: {}ms",className,methodName,stopWatch.getTotalTimeMillis());

        return result;
    }

    @Before(value = "within(@org.springframework.stereotype.Service *)")
    public void beforeEntering(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        log.info("Entering {}.{} with below parameters details", className, methodName);

        var argName = methodSignature.getParameterNames();
        var argType = methodSignature.getParameterTypes();
        var argValue = joinPoint.getArgs();

        IntStream.range(0, argName.length).forEach(i -> {
            log.info("param_{} ([{}] : [{}]) - [{}]", i, argName[i], argType[i].getSimpleName(), cleanString.apply(argValue[i]));
        });

        log.info("return type: {}", methodSignature.getReturnType().getSimpleName());
        log.info("method modifier: {}", Modifier.toString(methodSignature.getModifiers()));
        log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

    }

    @AfterReturning(value = "within(@org.springframework.stereotype.Service *)", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("{}.{} exited normally with return value as {}", className, methodName, returnObject);

    }
}