package ru.rndev.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    @Pointcut("within(ru.rndev.*.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("within(ru.rndev.*.*Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("within(ru.rndev.*.*Handler)")
    public void isHandlerLayer() {
    }

}
