package io.lukeliang.automation.packageanalyse.classreader.basicinfo.attribute;

import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantPool;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U4;

public class Signature extends AttributeInfo{

	public int signature_index;
	
	public Signature(ConstantPool cp, int nameIndex) {
		super(cp, nameIndex);
	}
	
	 @Override
	    public void read(InputStream inputStream) {
	        length = (int) U4.read(inputStream);
	        signature_index = U2.read(inputStream);
	    }

}
