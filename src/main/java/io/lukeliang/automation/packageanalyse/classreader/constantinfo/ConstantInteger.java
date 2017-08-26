package io.lukeliang.automation.packageanalyse.classreader.constantinfo;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantInfo;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U4;

/**
 * Created by wanginbeijing on 2017/1/24.
 */
public class ConstantInteger extends ConstantInfo {
    public long value;

    @Override
    public void read(InputStream inputStream) {
        value = U4.read(inputStream);
    }
}
