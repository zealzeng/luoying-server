package com.whlylc.ioc;

/**
 * Application environment
 */
public interface Environment {

    String getProperty(String key, String defaultValue);

	String getProperty(String key);

	int getIntProperty(String key, int defaultValue);

	long getLongProperty(String key, long defaultValue);

}
