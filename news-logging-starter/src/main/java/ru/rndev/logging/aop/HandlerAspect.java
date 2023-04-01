package ru.rndev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class HandlerAspect {

    @AfterReturning(value = "ru.rndev.logging.aop.CommonPointcuts.isHandlerLayer()", returning = "result")
    public void addLogging(Object result){
        log.error("Response: {}", result);
    }

}
