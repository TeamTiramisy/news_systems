package ru.rndev.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("within(ru.rndev.service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("within(ru.rndev.controller.*Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("within(ru.rndev.handler.*Handler)")
    public void isHandlerLayer() {
    }

}
