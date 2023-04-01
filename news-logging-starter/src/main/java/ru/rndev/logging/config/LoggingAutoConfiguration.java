package ru.rndev.logging.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rndev.logging.aop.CommonPointcuts;
import ru.rndev.logging.aop.ControllerAspect;
import ru.rndev.logging.aop.HandlerAspect;
import ru.rndev.logging.aop.ServiceAspect;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.news.logging", name = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {


    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    public CommonPointcuts commonPointcuts(){
        return new CommonPointcuts();
    }

    @Bean
    public ControllerAspect controllerAspect(){
        return new ControllerAspect();
    }

    @Bean
    public HandlerAspect handlerAspect(){
        return new HandlerAspect();
    }

    @Bean
    public ServiceAspect serviceAspect(){
        return new ServiceAspect();
    }

}
