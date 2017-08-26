package io.lukeliang.automation.packageanalyse.analysor.bean;

public class BaseBean {
	public String name;
	public String type;
	public boolean isGeneric;
	public String genericType;

	static enum Types {
		Method, Parameter, Interface, Class, Result;
	}

}
