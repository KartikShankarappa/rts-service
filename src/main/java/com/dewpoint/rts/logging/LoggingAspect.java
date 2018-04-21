package com.dewpoint.rts.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

import java.util.Arrays;

@Configuration
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    /**
//     * Pointcut that matches all repositories, services and REST endpoints
//     */
//    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
//            " || within(@org.springframework.stereotype.Service *)" +
//            " || within(@org.springframework.stereotype.Component *)" +
//            " || within(@org.springframework.web.bind.annotation.RestController *)")

    /**
     * Pointcut that matches com.dewpoint.rts package and sub packages only
     */
    @Pointcut("within(com.dewpoint.rts..*)")
    public void customSpringBeanPointcut() {
    }

    @Around("customSpringBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}",
                            joinPoint.getSignature().getDeclaringTypeName(),
                            joinPoint.getSignature().getName(),
                            Arrays.toString(joinPoint.getArgs()));
        }
        try {
            stopWatch.start(joinPoint.toShortString());
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }

            return result;

        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());

            throw e;
        } finally {
            stopWatch.stop();
            TaskInfo taskInfo = stopWatch.getLastTaskInfo();
            log.info("{} {}",
                    keyValue("timing-ms", ""+taskInfo.getTimeMillis()),
                    keyValue("timing-component", joinPoint.getSignature().getDeclaringTypeName()));
        }
    }

    private String keyValue(String key, String value) {
        return key + " = " + value;
    }
}

