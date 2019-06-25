package com.whlylc.ioc.spring;

import com.whlylc.ioc.Environment;
import com.whlylc.util.NumberUtils;

/**
 * @author Zeal
 */
public class SpringEnvironment implements Environment {

    private org.springframework.core.env.Environment springEnvironment = null;

    public SpringEnvironment(org.springframework.core.env.Environment springEnvironment) {
        this.springEnvironment = springEnvironment;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return this.springEnvironment.getProperty(key, defaultValue);
    }

    @Override
    public String getProperty(String key) {
        return this.springEnvironment.getProperty(key);
    }

    @Override
    public int getIntProperty(String key, int defaultValue) {
        String value = this.springEnvironment.getProperty(key);
        return NumberUtils.toInt(value, defaultValue);
    }

    @Override
    public long getLongProperty(String key, long defaultValue) {
        String value = this.springEnvironment.getProperty(key);
        return NumberUtils.toLong(value, defaultValue);
    }
}
