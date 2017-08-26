package io.lukeliang.automation.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import io.lukeliang.automation.packageanalyse.analysor.BaseType;

public class CommonUtils {

	private final static TypeToken<Map<String, Object>> MAP_TYPE_TOKEN = new TypeToken<Map<String, Object>>(){};
	private final static TypeToken<List<Object>> LIST_TYPE_TOKEN = new TypeToken<List<Object>>(){};

	public static String getMd5(byte[] bytes) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger bi = new BigInteger(1, md5.digest(bytes));
		String value = bi.toString(16);
		return value;
	}
	

    /***
     *
     *
     * @param inputvalue
     * @param parameterTypes
     * @return
     * @throws Exception
     */
    public static Object[] transFrom(String inputvalue, String parameterTypes) throws Exception {
        Gson gson = new Gson();

        if (StringUtils.isEmpty(parameterTypes) && StringUtils.isEmpty(inputvalue)) {
            return new Object[]{};
        }
        //参数类型
        String[] parameterType = parameterTypes.split(",");
        //参数值
        String[] args = inputvalue.split("#");
        //输入参数,输入值 个数必须相等
        Assert.isTrue(parameterType.length == args.length, "输入参数,输入值 个数必须相等");

        Object[] objects = new Object[args.length];

        for (int i = 0; i < parameterType.length; i++) {
            String temp = parameterType[i];
            if (temp.equals("java.lang.String")) {
                objects[i] = args[i];
            } else if (temp.contains("[]")) {
                String[] tmp = args[i].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                Object[] o = new Object[temp.length()];
                for (int j = 0; j < tmp.length; j++) {
                    o[j] = tmp[j];
                }
                objects[i] = o;
            } else if (temp.contains("java.util.List")) {
                List<Object> arrayList = jsonToArrayList(inputvalue, Object.class);
                objects[i] = arrayList;
            } else if (temp.contains("java.util.Map") || temp.contains("class:")) {
                inputvalue = args[i];
                Map<String, Object> retMap2 = gson.fromJson(inputvalue, MAP_TYPE_TOKEN.getType());
                retMap2.remove("class");
                objects[i] = retMap2;
            } else {
                objects[i] = args[i];
            }
        }
        return objects;
    }

    public static <T> List<T> jsonToArrayList(String json, Class<T> clazz) {
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, LIST_TYPE_TOKEN.getType());
        List<T> arrayList = new ArrayList<T>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
    
    public static void main(String[] args) {
		System.out.println(BaseType.valueOf("int"));
	}
    
}
