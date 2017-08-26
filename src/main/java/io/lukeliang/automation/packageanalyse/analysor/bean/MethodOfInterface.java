package io.lukeliang.automation.packageanalyse.analysor.bean;

import java.util.List;

public class MethodOfInterface extends BaseBean {
	ResultOfMethod resultType;
	List<ParameterOfMethod> parameterList;

	public ResultOfMethod getResultType() {
		return resultType;
	}

	public void setResultType(ResultOfMethod resultType) {
		this.resultType = resultType;
	}

	public List<ParameterOfMethod> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterOfMethod> parameterList) {
		this.parameterList = parameterList;
	}

}
