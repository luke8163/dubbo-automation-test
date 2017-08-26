package io.lukeliang.automation.packageanalyse.analysor;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.type.TypeReference;

public class VariableTypeReference extends TypeReference<Object> {

	public Type getType(){
		return _type;
	}
	
	public static void main(String[] args) {
		VariableTypeReference n = new VariableTypeReference();
	}
}
