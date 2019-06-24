package com.whlylc.ioc.utils;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.ConcurrentApplicationContext;
import com.whlylc.ioc.DefaultEnvironment;
import com.whlylc.ioc.Environment;

import java.io.File;

/**
 * Utility to create application context
 */
public class ApplicationContexts {

    public static ApplicationContext createApplicationContext() {
        ApplicationContext ctx = new ConcurrentApplicationContext();
        return ctx;
    }

    public static ApplicationContext createApplicationContext(File configDirOrFile) {
        Environment environment = new DefaultEnvironment(configDirOrFile);
        ApplicationContext ctx = new ConcurrentApplicationContext(environment);
        return ctx;
    }

    public static ApplicationContext createApplicationContext(File configDir, String configFileName) {
        Environment environment = new DefaultEnvironment(configDir, configFileName);
        ApplicationContext ctx = new ConcurrentApplicationContext(environment);
        return ctx;
    }

    public static ApplicationContext createApplicationContext(String sysEnvConfigDir, String configFileName) {
        Environment environment = new DefaultEnvironment(sysEnvConfigDir, configFileName);
        ApplicationContext ctx = new ConcurrentApplicationContext(environment);
        return ctx;
    }

    public static ApplicationContext createApplicationContext(String configDirOrFile) {
        Environment environment = new DefaultEnvironment(configDirOrFile);
        ApplicationContext ctx = new ConcurrentApplicationContext(environment);
        return ctx;
    }











}
