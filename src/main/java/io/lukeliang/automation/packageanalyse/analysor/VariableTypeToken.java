package io.lukeliang.automation.packageanalyse.analysor;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public class VariableTypeToken extends TypeToken<Object>{
	
	public VariableTypeToken(Type type) {
//		super(type);
	}
	public static VariableTypeToken buildTypeToken(Type type) {
		return new VariableTypeToken(type);
	}
}
