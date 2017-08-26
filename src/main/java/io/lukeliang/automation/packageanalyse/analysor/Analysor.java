package io.lukeliang.automation.packageanalyse.analysor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.lukeliang.automation.packageanalyse.analysor.bean.InterfacePo;
import io.lukeliang.automation.packageanalyse.analysor.bean.MethodOfInterface;
import io.lukeliang.automation.packageanalyse.analysor.bean.ParameterOfMethod;
import io.lukeliang.automation.packageanalyse.analysor.bean.ResultOfMethod;
import io.lukeliang.automation.packageanalyse.classloader.GetClass;
import sun.misc.Unsafe;

public class Analysor {
	
	private final static TypeFactory factory = TypeFactory.defaultInstance();
	
	static Unsafe unsafe;
	static {
		try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe =  (Unsafe) f.get(null);
        } catch (Exception e) {
        }
	}

	public static List<InterfacePo> analyseByJar(File[] jarFiles) {
		List<InterfacePo> result = new ArrayList<>();
		for (File file : jarFiles) {

			try {
				for (String className : GetClass.getClasssFromJarFile(file)) {
//					 if (className.indexOf("CommonStmAppDubbo") < 0 ) {
//					 continue;
//					 }
					URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
					Class<?> c = classLoader.loadClass(className);
					if (!c.isInterface()){
						continue;
					}
					
					InterfacePo interfacePo = new InterfacePo();
					result.add(interfacePo);
					
					interfacePo.type = c.getName();
					
					List<MethodOfInterface> methodList = new ArrayList<>();
					interfacePo.setMethods(methodList);
					
					Method[] methods = c.getMethods();
					for (Method m : methods) {
						MethodOfInterface method = new MethodOfInterface();
						List<ParameterOfMethod> parameterList = new ArrayList<>();
						method.setParameterList(parameterList);
						
						method.name = m.getName();
						
						ResultOfMethod returned = new ResultOfMethod();
						if (m.getGenericReturnType().getTypeName().equals(m.getReturnType().getName())) {
							returned.type = m.getReturnType().getName();
							returned.isGeneric = false;
						} else {
							returned.type = m.getReturnType().getName();
							
							Type genericType = m.getGenericReturnType();
							JavaType type = factory.constructType(genericType);
							System.out.println("generic: " + type.containedTypeCount() + ";" + type.getTypeName() + ";" );
//							returned.genericType = ((TypeVariable)m.getGenericReturnType()).getGenericDeclaration().getTypeParameters()[0].getTypeName();
						}
						
						// System.out.print("
						// "+m.getReturnType().getName())ss;
						System.out.print("  " + m.getGenericReturnType().getTypeName());
						System.out.print(" " + m.getName() + "(");

						for (Parameter parameter : m.getParameters()) {
							System.out.print(
									parameter.getParameterizedType().getTypeName() + " " + parameter.getName() + ",");
//							Object obj = unsafe.allocateInstance(parameter.getType());
//							System.out.println();
//							System.out.println("Generic Type:" + (obj.getClass().getDeclaredConstructors()[0].getTypeParameters()));
							
							if (m.getName().equals("comStmApp")) {
//								return parameter;
							}
						}
							// System.out.print(parameter.getType().getName()
							// + " " + parameter.getName() + ",");

						System.out.println(")");
					}
					
					// classList.add(c);
					// System.out.println(c.newInstance().toString());
					System.out.println(c.getName());
//					if (c.getSuperclass() != null && c.getSuperclass().getName() != "java.lang.Object")
//						System.out.println(((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0]);
					for (Field field : c.getDeclaredFields()) {
						System.out.println("field:" + field.getGenericType().getTypeName() + " " + field.getName());
					}
					

				}
			} catch (SecurityException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void test(Type type) {
		Type resultType;
		if (type instanceof Class<?>) {
            // Important: remove possible bindings since this is type-erased thingy
            resultType = _fromClass(context, (Class<?>) type, EMPTY_BINDINGS);
        }
        // But if not, need to start resolving.
        else if (type instanceof ParameterizedType) {
//            resultType = _fromParamType(context, (ParameterizedType) type, bindings);
            resultType = ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        else if (type instanceof JavaType) { // [databind#116]
            // no need to modify further if we already had JavaType
            return (JavaType) type;
        }
        else if (type instanceof GenericArrayType) {
            resultType = _fromArrayType(context, (GenericArrayType) type, bindings);
        }
        else if (type instanceof TypeVariable<?>) {
            resultType = _fromVariable(context, (TypeVariable<?>) type, bindings);
        }
        else if (type instanceof WildcardType) {
            resultType = _fromWildcard(context, (WildcardType) type, bindings);
        } else {
            // sanity check
            throw new IllegalArgumentException("Unrecognized Type: "+((type == null) ? "[null]" : type.toString()));
        }
	}
}
