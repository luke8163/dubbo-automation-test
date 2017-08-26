package io.lukeliang.automation.packageanalyse.classreader.constantinfo;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class ConstantMemberRef extends ConstantInfo{
    public int classIndex;
    public int nameAndTypeIndex;


    @Override
    public void read(InputStream inputStream) {
        classIndex= U2.read(inputStream);
        nameAndTypeIndex=U2.read(inputStream);
    }
}
