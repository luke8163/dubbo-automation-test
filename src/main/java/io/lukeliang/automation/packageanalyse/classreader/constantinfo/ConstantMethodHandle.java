package io.lukeliang.automation.packageanalyse.classreader.constantinfo;



import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U1;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class ConstantMethodHandle extends ConstantInfo {
    public short referenceKind;
    public int referenceIndex;

    @Override
    public void read(InputStream inputStream) {
        referenceKind = U1.read(inputStream);
        referenceIndex = U2.read(inputStream);
    }
}
