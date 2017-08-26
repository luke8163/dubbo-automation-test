package io.lukeliang.automation.packageanalyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import io.lukeliang.automation.packageanalyse.classloader.GetClass;

public class TestMain {

	public static void main(String[] args) {
		try {
			// 第一种 配置成文件格式
//			File file = new File("D:\\jarload\\test.txt");
//			BufferedReader in = new BufferedReader(new FileReader(file));
//			String s = new String();
//			while ((s = in.readLine()) != null) {
//
//				 URL url = new URL(s);
//				 s = null;
//				
//				 URLClassLoader myClassLoader = new URLClassLoader(new URL[] {
//				 url }, Thread.currentThread()
//				 .getContextClassLoader());
//				 Class<? extends AbstractAction> myClass = (Class<? extends
//				 AbstractAction>)
//				 myClassLoader.loadClass("com.java.jarloader.TestAction");
//				 AbstractAction action = (AbstractAction)
//				 myClass.newInstance();
//				 String str = action.action();
//				 System.out.println(str);

				// 第二种
				URL url1 = new URL("file: /Users/LukeLiang/.m2/repository/com/lemon/lemon-bap-rs/1.0-dev-SNAPSHOT/lemon-bap-rs-1.0-dev-SNAPSHOT.jar");
				URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 },
						Thread.currentThread().getContextClassLoader());
				List<Class> classList = new ArrayList<Class>();
				for (String name : GetClass.getNames()){
					name = name.replaceAll(".class", "");
					name = name.replaceAll("/", ".");
					name = "com.lemon.bap.domain.BaseReturnResult";
					Class<?> myClass1 = myClassLoader1.loadClass(name);
					classList.add(myClass1);
					System.out.println(myClass1.newInstance().toString());
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
