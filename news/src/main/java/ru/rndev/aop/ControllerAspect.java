package ru.rndev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("ru.rndev.aop.CommonPointcuts.isControllerLayer() && " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){}

    @Pointcut("ru.rndev.aop.CommonPointcuts.isControllerLayer() && " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void hasPostMapping(){}

    @Pointcut("ru.rndev.aop.CommonPointcuts.isControllerLayer() && " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void hasDeleteMapping(){}

    @Pointcut("ru.rndev.aop.CommonPointcuts.isControllerLayer() && " +
                     "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void hasPutMapping(){}


    @Before("hasGetMapping()")
    public void addLoggingGet(){
        log.info("Request: Method GET");
    }

    @Before("hasPostMapping()")
    public void addLoggingPost(JoinPoint joinPoint){
        log.info("Request: Method POST, body {}", joinPoint.getArgs());
    }

    @Before("hasPutMapping()")
    public void addLoggingPut(JoinPoint joinPoint){
        log.info("Request: Method PUT, body {}", joinPoint.getArgs()[1]);
    }

    @Before("hasDeleteMapping()")
    public void addLoggingDelete(){
        log.info("Request: Method DELETE");
    }

    @AfterReturning(value = "ru.rndev.aop.CommonPointcuts.isControllerLayer()", returning = "result")
    public void addLoggingReturning(Object result){
        log.info("Response: {}", result);
    }

}
