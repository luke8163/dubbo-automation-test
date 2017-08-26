package io.lukeliang.automation.packageanalyse.analysor;

public enum BaseType {
	VOID("V", "void", Void.class),
	BYTE("B", "byte", Byte.class),
	CHAR("C", "char", Character.class),
	DOUBLE("D", "double", Double.class),
	FLOAT("F", "float", Float.class),
	INT("I", "int", Integer.class),
	LONG("J", "long", Long.class),
	SHORT("S", "short", Short.class),
	BOOLEAN("Z", "boolean", Boolean.class);
	
	private String typeSign;

	private String typeName;
	
	private Class<?> packageType;
	
	private BaseType(String typeSign, String typeName, Class<?> packageType){
		this.typeSign = typeSign;
		this.typeName = typeName;
		this.packageType = packageType;
	}

	public String getTypeSign() {
		return typeSign;
	}

	public void setTypeSign(String typeSign) {
		this.typeSign = typeSign;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Class<?> getPackageType() {
		return packageType;
	}

	public void setPackageType(Class<?> packageType) {
		this.packageType = packageType;
	}

	
	
}
