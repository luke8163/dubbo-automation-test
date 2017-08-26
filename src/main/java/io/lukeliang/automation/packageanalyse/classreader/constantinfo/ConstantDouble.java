package io.lukeliang.automation.packageanalyse.classreader.constantinfo;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U4;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public class ConstantDouble extends ConstantInfo {
    public long highValue;
    public long lowValue;

    @Override
    public void read(InputStream inputStream) {
        highValue = U4.read(inputStream);
        lowValue = U4.read(inputStream);
    }
}
