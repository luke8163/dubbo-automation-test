package io.lukeliang.automation.packageanalyse.classreader.constantinfo;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class ConstantInvokeDynamic extends ConstantInfo {
    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;

    @Override
    public void read(InputStream inputStream) {
        bootstrapMethodAttrIndex = U2.read(inputStream);
        nameAndTypeIndex = U2.read(inputStream);
    }
}
