package org.apache.alan.wrokflow.utils;


import org.apache.alan.wrokflow.enums.Env;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 */

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;

    public SpringContextHolder() {
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
        environment = applicationContext.getEnvironment();
    }

    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        checkApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        checkApplicationContext();
        return applicationContext.containsBean(name);
    }

    public static <T> Map<String, T> getBeans(Class<T> requiredType) {
        checkApplicationContext();
        return applicationContext.getBeansOfType(requiredType);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入");
        }
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static Env getCurrentEnv() {
        if(environment == null){
            return Env.valueOf("dev");
        }
        return Env.valueOf(environment.getActiveProfiles()[0]);
    }
}

