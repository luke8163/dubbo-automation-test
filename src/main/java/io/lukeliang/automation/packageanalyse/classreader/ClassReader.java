package io.lukeliang.automation.packageanalyse.classreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.lukeliang.automation.packageanalyse.classreader.basicinfo.MemberInfo;
import io.lukeliang.automation.packageanalyse.classreader.basicinfo.attribute.CodeAttribute;
import io.lukeliang.automation.packageanalyse.classreader.basicinfo.attribute.Signature;
import io.lukeliang.automation.packageanalyse.classreader.basicinfo.instruction.InstructionTable;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U2;
import io.lukeliang.automation.packageanalyse.classreader.basictype.U4;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantClass;
import io.lukeliang.automation.packageanalyse.classreader.constantinfo.ConstantUtf8;

/**
 * Created by wanginbeijing on 2017/1/24.
 */
public class ClassReader {
    public static void read(InputStream inputStream) {
//        File file = new File(classPath);
        try {
            //FileInputStream inputStream = new FileInputStream(file);
            ClassFile classFile = new ClassFile();
            classFile.magic = U4.read(inputStream);
            classFile.minorVersion = U2.read(inputStream);
            classFile.majorVersion = U2.read(inputStream);

            //解析常量池
            int constant_pool_count = U2.read(inputStream);
            ConstantPool constantPool = new ConstantPool(constant_pool_count);
            constantPool.read(inputStream);
            
            //获取常量池信息
            constantPool.printConstanPoolInfo(constantPool);
            

            //获取类信息
            classFile.accessFlag = U2.read(inputStream);
            int classIndex = U2.read(inputStream);
            ConstantClass clazz = (ConstantClass) constantPool.cpInfo[classIndex];  //获取类名，并将其转化为ContantClass类
            ConstantUtf8 className = (ConstantUtf8) constantPool.cpInfo[clazz.nameIndex];  
            classFile.className = className.value;
            System.out.print("classname:" + classFile.className + "\n");

            //获取父类信息
            int superIndex = U2.read(inputStream);
            ConstantClass superClazz = (ConstantClass) constantPool.cpInfo[superIndex];
            ConstantUtf8 superclassName = (ConstantUtf8) constantPool.cpInfo[superClazz.nameIndex];
            classFile.superClass = superclassName.value;
            System.out.print("superclass:" + classFile.superClass + "\n");

            //获取接口信息
            classFile.interfaceCount = U2.read(inputStream);
            classFile.interfaces = new String[classFile.interfaceCount];
            for (int i = 0; i < classFile.interfaceCount; i++) {
                int interfaceIndex = U2.read(inputStream);
                ConstantClass interfaceClazz = (ConstantClass) constantPool.cpInfo[interfaceIndex];
                ConstantUtf8 interfaceName = (ConstantUtf8) constantPool.cpInfo[interfaceClazz.nameIndex];
                classFile.interfaces[i] = interfaceName.value;
                System.out.print("interface:" + interfaceName.value + "\n");
            }
            System.out.print("\n");

            //获取字段信息
            classFile.fieldCount = U2.read(inputStream);
            classFile.fields = new MemberInfo[classFile.fieldCount];
            for (int i = 0; i < classFile.fieldCount; i++) {
                MemberInfo fieldInfo = new MemberInfo(constantPool);
                fieldInfo.read(inputStream);
                System.out.print("field:" + ((ConstantUtf8) constantPool.cpInfo[fieldInfo.nameIndex]).value + ", ");
                System.out.print("desc:" + ((ConstantUtf8) constantPool.cpInfo[fieldInfo.descriptorIndex]).value + "\n");
            }
            System.out.print("\n");

            //获取方法信息
            classFile.methodCount = U2.read(inputStream);
            classFile.methods = new MemberInfo[classFile.methodCount];
            for (int i = 0; i < classFile.methodCount; i++) {
                MemberInfo methodInfo = new MemberInfo(constantPool);
                methodInfo.read(inputStream);
                System.out.print("method:" + ((ConstantUtf8) constantPool.cpInfo[methodInfo.nameIndex]).value + "(), ");
                System.out.print("desc:" + ((ConstantUtf8) constantPool.cpInfo[methodInfo.descriptorIndex]).value + "\n");
                for (int j = 0; j < methodInfo.attributesCount; j++) {
                    if (methodInfo.attributes[j] instanceof CodeAttribute) {
                        CodeAttribute codeAttribute = (CodeAttribute) methodInfo.attributes[j];
                        for (int m = 0; m < codeAttribute.codeLength; m++) {
                            short code = codeAttribute.code[m];
                            System.out.print(InstructionTable.getInstruction(code) + "\n");
                        }
                    } else if (methodInfo.attributes[j] instanceof Signature) {
                    	Signature signature = (Signature) methodInfo.attributes[j];
                    	System.out.println(((ConstantUtf8) constantPool.cpInfo[signature.nameIndex]).value+":"+((ConstantUtf8) constantPool.cpInfo[signature.signature_index]).value);
                    }
                }
                System.out.print("\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
