package io.lukeliang.automation.packageanalyse.classloader;

import java.net.URLClassLoader;

public class ClassLoaderByJar {
	
	private String md5;

	private final URLClassLoader classLoader;
	
	private long timestamp;
	
	public ClassLoaderByJar(URLClassLoader classLoader) {
		this(classLoader, System.currentTimeMillis());
	}
	
	public ClassLoaderByJar(URLClassLoader classLoader, long timestamp) {
		this.classLoader = classLoader;
		this.timestamp = timestamp;

	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public URLClassLoader getClassLoader() {
		return classLoader;
	}
	
	
}
