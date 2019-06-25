package com.whlylc.ioc.spring;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.Environment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Zeal
 */
public class SpringApplicationContext implements ApplicationContext {

    private org.springframework.context.ApplicationContext springAppCtx = null;

    private SpringEnvironment springEnvironment = null;

    public SpringApplicationContext(org.springframework.context.ApplicationContext  springAppCtx) {
        this.springAppCtx = springAppCtx;
        this.springEnvironment = new SpringEnvironment(this.springAppCtx.getEnvironment());
    }

    public SpringApplicationContext(String... configLocations) {
        this.springAppCtx = new FileSystemXmlApplicationContext(configLocations);
        this.springEnvironment = new SpringEnvironment(this.springAppCtx.getEnvironment());
    }

    public SpringApplicationContext(Class<?>... annotatedClasses) {
        this.springAppCtx = new AnnotationConfigApplicationContext(annotatedClasses);
        this.springEnvironment = new SpringEnvironment(this.springAppCtx.getEnvironment());
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return springAppCtx.getBean(requiredType);
    }

    @Override
    public Object getBean(String name) {
        return springAppCtx.getBean(name);
    }

    @Override
    public String[] getBeanNames() {
        return springAppCtx.getBeanDefinitionNames();
    }

    @Override
    public Environment getEnvironment() {
        return this.springEnvironment;
    }
}
