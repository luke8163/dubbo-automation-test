package io.lukeliang.automation.packageanalyse.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GetClass {

	public static List<String> getNames() {

		List<String> nameList = new ArrayList<>();

		JarFile jfile = null;
		try {

			jfile = new JarFile(
					"//Users//LukeLiang//.m2//repository//com//lemon//lemon-bap-rs//1.0-dev-SNAPSHOT//lemon-bap-rs-1.0-dev-SNAPSHOT.jar");
		} catch (IOException e) {

			e.printStackTrace();
		}
		Enumeration<JarEntry> files = jfile.entries();

		while (files.hasMoreElements()) {

			JarEntry entry = files.nextElement();
			if (entry.getName().endsWith(".class")) {
				System.out.println(entry.getName());
				nameList.add(entry.getName());
			}
		}
		return nameList;
	}

	public static List<String> getClasssFromJarFile(File file) throws IOException {

		JarFile jarFile = null;
		InputStream in = null;
		try {
			List<String> clazzs = new ArrayList<String>();

			jarFile = new JarFile(file);
			List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

			Enumeration<JarEntry> ee = jarFile.entries();
			while (ee.hasMoreElements()) {
				JarEntry entry = (JarEntry) ee.nextElement();
				// 过滤我们出满足我们需求的东西
				if (entry.getName().endsWith(".class")) {
					jarEntryList.add(entry);
				}
			}
			for (JarEntry entry : jarEntryList) {
				in = jarFile.getInputStream(entry);
				String className = entry.getName().replace('/', '.');
				className = className.substring(0, className.length() - 6);
				clazzs.add(className);
//				ClassReader.read(in);
				// System.out.println(className);
			}

			return clazzs;
		} finally {
			if (jarFile != null)
				jarFile.close();
			if (in != null) {
				in.close();
			}
		}
	}
}
