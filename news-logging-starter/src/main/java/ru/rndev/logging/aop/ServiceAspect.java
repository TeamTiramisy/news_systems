package ru.rndev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class ServiceAspect {

    @Around("ru.rndev.logging.aop.CommonPointcuts.isServiceLayer()")
    public Object addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("invoked method {} in {}, with args {}",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.debug("invoked method {} in {}, result {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), result);
            return result;
        } catch (Throwable exception){
            log.error("invoked method {} in {}, exception {}: {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass(), exception.getClass(), exception.getMessage());
            throw exception;
        } finally {
            log.debug("invoked method {} in {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass());
        }
    }

}

