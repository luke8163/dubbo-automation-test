package io.lukeliang.automation.packageanalyse.classreader.constantinfo;



import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class ConstantClass extends ConstantInfo {
    public int nameIndex;

    @Override
    public void read(InputStream inputStream) {
        nameIndex = U2.read(inputStream);
    }
}
