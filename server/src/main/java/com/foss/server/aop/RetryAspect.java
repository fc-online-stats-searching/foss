package com.foss.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Aspect
@Component
@Slf4j
public class RetryAspect {

    @Around("@annotation(RetryableBadRequest)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        return ((Mono<?>) joinPoint.proceed())
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal -> {
                            log.info("[" + methodName + "] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
                .onErrorResume(e -> {
                    log.info("[" + methodName + "]" + "onErrorResume");
                    return Mono.error(e);
                });
    }
}

