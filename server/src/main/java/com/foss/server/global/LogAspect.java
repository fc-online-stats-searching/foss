package com.foss.server.global;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around(value = "bean(*Controller)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Start StopWatch");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(String.valueOf(joinPoint.getSignature()));
        Object proceed = joinPoint.proceed();
        stopWatch.stop();

        log.info("\n{}{}s", stopWatch.prettyPrint(),stopWatch.getTotalTimeSeconds());
        return proceed;
    }

}
