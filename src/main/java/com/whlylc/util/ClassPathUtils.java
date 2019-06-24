/*
 * Copyright (c) 2016, All rights reserved.
 */
package com.whlylc.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;

/**
 * Utility tool to handle class path, file path and etc
 * @author Zeal 2016年3月24日
 */
public class ClassPathUtils {

	/**
	 * Get class path dir or lib path of PathUtils
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static File getClassPath() throws IOException,SecurityException {
		return getClassPath(ClassPathUtils.class);
	}

	/**
	 * Get class path dir or lib path of clazz
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static File getClassPath(Class<?> clazz) throws IOException,SecurityException {

		ClassLoader classLoader = ClassUtils.getClassLoader(clazz);
		URL url = classLoader.getResource("");
		//No class path dir is while running java app, it should be loaded by jar lib
		if (url == null) {
			CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
			if (codeSource == null) {
				throw new IOException("Failed to get class path");
			}
			url = codeSource.getLocation();
		}

		try {
			return new File(url.toURI());
		}
		catch (URISyntaxException e) {
			throw new IOException("Failed to get class path", e);
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getClassPath());
	}

}
