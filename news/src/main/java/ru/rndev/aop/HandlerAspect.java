package ru.rndev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class HandlerAspect {

    @AfterReturning(value = "ru.rndev.aop.CommonPointcuts.isHandlerLayer()", returning = "result")
    public void addLogging(Object result){
        log.info("Response: {}", result);
    }

}
