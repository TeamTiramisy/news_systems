package ru.rndev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Around("ru.rndev.aop.CommonPointcuts.isServiceLayer()")
    public Object addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("invoked method {} in {}, with args {}",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("invoked method {} in {}, result {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), result);
            return result;
        } catch (Throwable exception){
            log.info("invoked method {} in {}, exception {}: {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), exception.getClass(), exception.getMessage());
            throw exception;
        } finally {
            log.info("invoked method {} in {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass());
        }
    }

}

