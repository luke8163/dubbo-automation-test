package io.lukeliang.automation.packageanalyse.analysor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class VariableParameterizedType implements ParameterizedType{

	Type[] actualTypeArguments = null;
	
	Type rawType;
	Type ownerType;
	
	@Override
	public Type[] getActualTypeArguments() {
		// TODO Auto-generated method stub
		return actualTypeArguments;
	}

	@Override
	public Type getRawType() {
		// TODO Auto-generated method stub
		return rawType;
	}

	@Override
	public Type getOwnerType() {
		// TODO Auto-generated method stub
		return ownerType;
	}

	public void setActualTypeArguments(Type[] actualTypeArguments) {
		this.actualTypeArguments = actualTypeArguments;
	}

	public void setRawType(Type rawType) {
		this.rawType = rawType;
	}

	public void setOwnerType(Type ownerType) {
		this.ownerType = ownerType;
	}

	
}
