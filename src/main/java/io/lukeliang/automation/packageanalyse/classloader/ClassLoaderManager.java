package io.lukeliang.automation.packageanalyse.classloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.lukeliang.automation.packageanalyse.analysor.Analysor;
import io.lukeliang.automation.packageanalyse.analysor.VariableParameterizedType;
import io.lukeliang.automation.packageanalyse.analysor.VariableTypeToken;

@Component
public class ClassLoaderManager {

	@Value("${automation.test.jar.max.living.time:43200000}")
	private long maxLivingtime;

	private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

	private final Map<String, ClassLoaderByJar> map = new ConcurrentHashMap<>();

	private ClassLoaderManager() {
	};

	@PostConstruct
	private void initialize() {
		ses.scheduleWithFixedDelay(() -> this.retireClassLoader(), 60L, 60L, TimeUnit.SECONDS);
	}

	@PreDestroy
	private void destory() {

		for (Entry<String, ClassLoaderByJar> entry : map.entrySet()) {
			ClassLoaderByJar classLoaderByJar = entry.getValue();
			map.remove(entry.getKey());
			if (classLoaderByJar.getClassLoader() == null) {
				try {
					classLoaderByJar.getClassLoader().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (!ses.isShutdown())
			ses.shutdown();
		if (!ses.isTerminated()) {
			try {
				ses.awaitTermination(0, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void retireClassLoader() {
		for (Entry<String, ClassLoaderByJar> entry : map.entrySet()) {
			ClassLoaderByJar classLoaderByJar = entry.getValue();
			if (System.currentTimeMillis() - classLoaderByJar.getTimestamp() > maxLivingtime) {
				map.remove(entry.getKey());
				if (classLoaderByJar.getClassLoader() == null) {
					try {
						classLoaderByJar.getClassLoader().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private URLClassLoader registerClassLoaderByFile(File file) throws FileNotFoundException {
		// String md5 = CommonUtils.getMd5ByFile(file);

		return (URLClassLoader) ClassLoader.getSystemClassLoader();
		/*
		 * String md5 = ""; ClassLoaderByJar classLoaderByJar = map.get(md5);
		 * URLClassLoader classLoader; if (classLoaderByJar == null) {
		 * ClassLoader cl = ClassLoader.getSystemClassLoader(); classLoader =
		 * new URLClassLoader(new URL[] {}, cl); classLoaderByJar = new
		 * ClassLoaderByJar(classLoader); classLoaderByJar.setMd5(md5);
		 * map.put(md5, classLoaderByJar); } else { classLoader =
		 * classLoaderByJar.getClassLoader(); }
		 * classLoaderByJar.setTimestamp(System.currentTimeMillis()); return
		 * classLoader;
		 */
	}

	public void loadJarByPath(String jarPath) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
			InstantiationException, IOException {

		System.out.println("start");
		// 系统类库路径
		File libPath = new File(jarPath);

		// 获取所有的.jar和.zip文件
		File[] jarFiles = libPath.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar") || name.endsWith(".zip");
			}
		});

		// List<Class> classList = new ArrayList<Class>();

		if (jarFiles != null) {
			// 从URLClassLoader类中获取类所在文件夹的方法
			// 对于jar文件，可以理解为一个存放class文件的文件夹
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			boolean accessible = method.isAccessible(); // 获取方法的访问权限
			URLClassLoader classLoader = null;
			try {
				if (accessible == false) {
					method.setAccessible(true); // 设置方法的访问权限
				}
				// 获取系统类加载器

				for (File file : jarFiles) {
					classLoader = registerClassLoaderByFile(file);
					URL url = file.toURI().toURL();
					try {
						method.invoke(classLoader, url);
						System.out.println("读取jar文件[name={}]" + file.getName());

						// LOG.debug("读取jar文件[name={}]", file.getName());
					} catch (Exception e) {
						System.out.println("读取jar文件[name={}]失败" + file.getName());
						// LOG.error("读取jar文件[name={}]失败", file.getName());
					}

				}

				Analysor.analyseByJar(jarFiles);

				try {

					ObjectMapper map = new ObjectMapper();
					// TypeReference<Map<String, String>> type = new
					// TypeReference<Map<String, String>>() {};
					// map.readerFor();
					map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
					// String value = map.writeValueAsString(new
					// ClassLoaderByJar(null));
					// System.out.println(value);

					Class c = Class.forName("io.lukeliang.automation.packageanalyse.analysor.VariableTypeReference");

					Class cType = Class.forName("com.fasterxml.jackson.core.type.TypeReference");

					Field _type = cType.getDeclaredField("_type");
//					boolean accessible = _type.isAccessible();
					_type.setAccessible(true);

					// Constructor constructor = c.getDeclaredConstructor();
					// constructor.setAccessible(true);
					
					TypeReference type = new TypeReference<Object>() {};
					// c.getDeclaredConstructors();
					VariableParameterizedType vpt = new VariableParameterizedType();
					System.out.println();
					/**
//					System.out.println("Type: " + type.getType().getTypeName());
					System.out.println("kk:" + ((ParameterizedType)param.getParameterizedType()).getActualTypeArguments()[0].getTypeName());
					for (Field field : param.getType().getDeclaredFields()){
						System.out.println(field.getType() + " " + field.getName());
					}
					System.out.println();
					
//					System.out.println(param.getParameterizedType().getTypeName());
					_type.set(type, (Type)param.getParameterizedType());
					Type p = param.getParameterizedType();
					
					TypeToken token = new VariableTypeToken(p);
					
					Gson gson = new Gson();
					String str = "null";
					do {
						str = str.replaceAll("null", "\"\"");
						System.out.println(str);
						Object object = gson.fromJson(str, p);
						
						str = map.writeValueAsString(object);
						
					} while (str.contains("null"));
					
					System.out.println(str);
					
					*/
//					System.out.println(p.getParameterizedType().getTypeName());

//					type = new TypeReference<Map<String, String>>() {};

					// _type.setAccessible(accessible);
//					Object kk = map.readValue("{\"uuid\":\"1\",\"object\":null,\"sysId\":\"\"}", type);
//					System.out.println(((Map)kk).get("code"));
					// (new ClassLoaderByJar(), type);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}

				// for (String className : )
				// Class<?> c =
				// classLoader.loadClass("com.lemon.bap.domain.swich.BaseReturnResult");
				// c.newInstance();
				// Method[] methods = c.getMethods();
				// for (Method m : methods) {
				// System.out.print(m.getReturnType().getName());
				// System.out.print(" "+ m.getName() + "(");
				// for (Class<?> parameter : m.getParameterTypes())
				// System.out.print(parameter.getName() + ",");
				// System.out.println(")");
				// }
				// System.out.println(c.getName());
			} finally {
				method.setAccessible(accessible);
				if (classLoader != null) {
					classLoader.close();
				}
			}

			// 避免出现内存泄漏
			// ClassLoader.getSystemClassLoader().loadClass(lastClassName);

		}
	}

	public static void main(String... strings) {
		ClassLoaderManager l = new ClassLoaderManager();
		try {
			try {
				l.loadJarByPath("/Users/LukeLiang/jar/");
			} catch (InstantiationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class TestA{
		public TestA(){
			throw new RuntimeException();
		}
	}
}
