package io.lukeliang.automation.test.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lukeliang.automation.packageanalyse.analysor.VariableParameterizedType;
import io.lukeliang.automation.packageanalyse.analysor.VariableTypeReference;

public class TestJaskson {

	public static void main(String... args) {
		
		try {
		
		ObjectMapper map = new ObjectMapper();
//		TypeReference<Map<String, String>> type = new TypeReference<Map<String, String>>() {};
//		map.readerFor();
		map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		String value = map.writeValueAsString(new ClassLoaderByJar(null));
//		System.out.println(value);
		
		Class c = Class.forName("io.lukeliang.automation.packageanalyse.analysor.VariableTypeReference");
		
		Class cType = Class.forName("com.fasterxml.jackson.core.type.TypeReference");
		
		Field _type = cType.getDeclaredField("_type");
		boolean accessible = _type.isAccessible();
		_type.setAccessible(true);

//		Constructor constructor = c.getDeclaredConstructor();
//		constructor.setAccessible(true);
		
		TypeReference type = (VariableTypeReference) c.newInstance();
//		c.getDeclaredConstructors();
		VariableParameterizedType vpt = new VariableParameterizedType();
		vpt.setRawType(new Type(){
					public String getTypeName() {
				        return "interface java.util.Map";
					}});
		_type.set(type, vpt);
		
		type = new TypeReference<Map<String,String>>() {};
		
//		_type.setAccessible(accessible);
		Map<String, String> kk = map.readValue("{\"ll\":\"1\"}", type);
		System.out.println(kk.get("ll"));
		//		(new ClassLoaderByJar(), type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

	}
}
