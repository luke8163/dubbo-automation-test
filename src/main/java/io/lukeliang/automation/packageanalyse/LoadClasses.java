package io.lukeliang.automation.packageanalyse;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClasses {

	public void loadJar() throws NoSuchMethodException, SecurityException, MalformedURLException{
		
		// ϵͳ���·��  
		File libPath = new File("jar�ļ�����·��");  
		  
		// ��ȡ���е�.jar��.zip�ļ�  
		File[] jarFiles = libPath.listFiles(new FilenameFilter() {  
		    public boolean accept(File dir, String name) {  
		        return name.endsWith(".jar") || name.endsWith(".zip");  
		    }  
		});  
		  
		if (jarFiles != null) {  
		    // ��URLClassLoader���л�ȡ�������ļ��еķ���  
		    // ����jar�ļ����������Ϊһ�����class�ļ����ļ���  
		    Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);  
		    boolean accessible = method.isAccessible();     // ��ȡ�����ķ���Ȩ��  
		    try {  
		        if (accessible == false) {  
		            method.setAccessible(true);     // ���÷����ķ���Ȩ��  
		        }  
		        // ��ȡϵͳ�������  
		        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();  
		        for (File file : jarFiles) {  
		            URL url = file.toURI().toURL();  
		            try {  
		                method.invoke(classLoader, url);    
		                //LOG.debug("��ȡjar�ļ�[name={}]", file.getName());  
		            } catch (Exception e) {  
		                //LOG.error("��ȡjar�ļ�[name={}]ʧ��", file.getName());  
		            }  
		        }  
		    } finally {  
		        method.setAccessible(accessible);  
		    }  
		}
	}
}
