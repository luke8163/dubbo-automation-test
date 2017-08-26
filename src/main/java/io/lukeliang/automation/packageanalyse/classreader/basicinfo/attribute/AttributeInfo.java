package io.lukeliang.automation.packageanalyse.classreader.basicinfo.attribute;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantPool;
import io.lukeliang.automation.packageanalyse.classreader.basicinfo.BasicInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U1;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U4;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantUtf8;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class AttributeInfo extends BasicInfo {
    public int nameIndex;
    public int length;
    public short[] info;

    public static final String CODE = "Code";
    
    public static final String SIGNATURE = "Signature";

    public AttributeInfo(ConstantPool cp, int nameIndex) {
    	
        super(cp);
        this.nameIndex = nameIndex;
    }

    @Override
    public void read(InputStream inputStream) {
        length = (int) U4.read(inputStream);
        info = new short[length];
        for (int i = 0; i < length; i++) {
            info[i] = U1.read(inputStream);
        }
    }

    public static AttributeInfo getAttribute(ConstantPool cp, InputStream inputStream) {
        int nameIndex = U2.read(inputStream);
        String name = ((ConstantUtf8) cp.cpInfo[nameIndex]).value;
        switch (name) {
            case CODE:
                return new CodeAttribute(cp, nameIndex);
            case SIGNATURE:
            	return new Signature(cp, nameIndex);
        }
        return new AttributeInfo(cp, nameIndex);

    }
}
