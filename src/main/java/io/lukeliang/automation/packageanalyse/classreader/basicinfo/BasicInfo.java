package io.lukeliang.automation.packageanalyse.classreader.basicinfo;


import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.ConstantPool;

/**
 * Created by wangxiandeng on 2017/1/25.
 */
public abstract class BasicInfo {
    public ConstantPool mCp;

    public BasicInfo(ConstantPool cp) {
        mCp = cp;
    }

    public abstract void read(InputStream inputStream);
}
