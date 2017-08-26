package io.lukeliang.automation.packageanalyse.analysor.bean;

import java.util.List;

public class InterfacePo extends BaseBean{
	
	List<MethodOfInterface> methods;

	public List<MethodOfInterface> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodOfInterface> methods) {
		this.methods = methods;
	}
	
}
