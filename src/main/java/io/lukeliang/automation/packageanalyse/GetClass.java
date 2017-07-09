package io.lukeliang.automation.packageanalyse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GetClass {
	public static void main(String[] args) {
		JarFile jfile = null;
		try {
			
			jfile = new JarFile("C:\\Users\\ê»\\.m2\\repository\\org\\apache\\maven\\maven-core\\2.0.9\\maven-core-2.0.9.jar");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Enumeration<JarEntry> files = jfile.entries();
		
		while (files.hasMoreElements()) {
			
			JarEntry entry = (JarEntry) files.nextElement();
			if (entry.getName().endsWith(".class")) {
				System.out.println(entry.getName());
			}
		}
	}
}
