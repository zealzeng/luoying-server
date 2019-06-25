package com.whlylc.ioc.utils;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.DefaultApplicationContext;
import com.whlylc.ioc.DefaultEnvironment;
import com.whlylc.ioc.Environment;
import com.whlylc.ioc.spring.SpringApplicationContext;

import java.io.File;

/**
 * Utility to create application context
 */
public class ApplicationContexts {

    public static DefaultApplicationContext createDefaultApplicationContext() {
        return new DefaultApplicationContext();
    }

    public static DefaultApplicationContext createDefaultApplicationContext(File configDirOrFile) {
        Environment environment = new DefaultEnvironment(configDirOrFile);
        return new DefaultApplicationContext(environment);
    }

    public static DefaultApplicationContext createDefaultApplicationContext(File configDir, String configFileName) {
        Environment environment = new DefaultEnvironment(configDir, configFileName);
        return new DefaultApplicationContext(environment);
    }

    public static DefaultApplicationContext createDefaultApplicationContext(String sysEnvConfigDir, String configFileName) {
        Environment environment = new DefaultEnvironment(sysEnvConfigDir, configFileName);
        return new DefaultApplicationContext(environment);
    }

    public static DefaultApplicationContext createDefaultApplicationContext(String configDirOrFile) {
        Environment environment = new DefaultEnvironment(configDirOrFile);
        return new DefaultApplicationContext(environment);
    }

    public static ApplicationContext createSpringApplicationContext(String... configLocations) {
        return new SpringApplicationContext(configLocations);
    }

    public static ApplicationContext createSpringApplicationContext(Class<?> annotatedClasses) {
        return new SpringApplicationContext(annotatedClasses);
    }

    public static ApplicationContext createSpringApplicationContext(org.springframework.context.ApplicationContext springAppCtx) {
        return new SpringApplicationContext(springAppCtx);
    }













}
