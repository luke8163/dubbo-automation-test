package io.lukeliang.automation.packageanalyse.classreader.basicinfo.attribute;

import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;

/**
 * Created by wangxiandeng on 2017/1/26.
 */
public class ExceptionTable {
    public int startPc;
    public int endPc;
    public int handlerPc;
    public int catchType;

    public void read(InputStream inputStream) {
        startPc = U2.read(inputStream);
        endPc = U2.read(inputStream);
        handlerPc = U2.read(inputStream);
        catchType = U2.read(inputStream);
        
        //这里可以增加一个输出输出异常范围的功能
        
        
    }
}
