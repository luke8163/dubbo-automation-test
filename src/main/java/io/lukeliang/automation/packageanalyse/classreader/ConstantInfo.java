package io.lukeliang.automation.packageanalyse.classreader;

import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantClass;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantDouble;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantFloat;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantInteger;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantInvokeDynamic;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantLong;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantMemberRef;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantMethodHandle;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantMethodType;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantNameAndType;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantString;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantUtf8;

/**
 * Created by wanginbeijing on 2017/1/24.
 */
public abstract class ConstantInfo {
    public static final short CONSTANT_Class = 7;
    public static final short CONSTANT_Fieldref = 9;
    public static final short CONSTANT_Methodref = 10;
    public static final short CONSTANT_InterfaceMethodref = 11;
    public static final short CONSTANT_String = 8;
    public static final short CONSTANT_Integer = 3;
    public static final short CONSTANT_Float = 4;
    public static final short CONSTANT_Long = 5;
    public static final short CONSTANT_Double = 6;
    public static final short CONSTANT_NameAndType = 12;
    public static final short CONSTANT_Utf8 = 1;
    public static final short CONSTANT_MethodHandle = 15;
    public static final short CONSTANT_MethodType = 16;
    public static final short CONSTANT_InvokeDynamic = 18;

    public short tag;

    public abstract void read(InputStream inputStream);

    public static ConstantInfo getConstantInfo(short tag) {
        switch (tag) {
            case CONSTANT_Class:
               return new ConstantClass();   
            case CONSTANT_Fieldref:
            case CONSTANT_Methodref:
            case CONSTANT_InterfaceMethodref:
                return new ConstantMemberRef();
            case CONSTANT_Long:
                return new ConstantLong();
            case CONSTANT_Double:
                return new ConstantDouble();
            case CONSTANT_String:
                return new ConstantString();
            case CONSTANT_Integer:
                return new ConstantInteger();
            case CONSTANT_Float:
                return new ConstantFloat();
            case CONSTANT_NameAndType:
                return new ConstantNameAndType();
            case CONSTANT_Utf8:
                return new ConstantUtf8();
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandle();
            case CONSTANT_MethodType:
                return new ConstantMethodType();
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamic();
                //这里是不是有个缺陷？？
        }
        return null;
    }

}
