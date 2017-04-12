package com.xfeng.database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lixianfeng on 2017/4/8.
 */

public class ClassUtil {

    /**
     * 获取非static和final修饰的属性
     *
     * @param clazz
     * @return
     */
    public static ArrayList<String> getFieldStrings(Class clazz) {

        ArrayList<String> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier))
                continue;
            list.add(field.getName());
        }

        return list;
    }

    /**
     * 获取非static和final到整型和String类型到属性
     *
     * @param clazz
     * @return
     */
    public static ArrayList<String> getDataBaseFieldStrings(Class clazz) {

        ArrayList<String> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier))
                continue;
            String typeStr = field.getType().getSimpleName();
            if (typeStr.equals("int")
                    || typeStr.equals("long")
                    || typeStr.equals("String")
                    || typeStr.equals("boolean")) {
                list.add(field.getName());
            }
        }

        return list;
    }

    /**
     * 获取：属性名称-属性类型
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getFieldMap(Class clazz) {

        TreeMap<String, String> map = new TreeMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier))
                continue;
            map.put(field.getName(), field.getType().getSimpleName());
        }

        return map;
    }

    /**
     * 获取:属性名称-属性对应的数据库类型
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getDataBaseMap(Class clazz) {

        TreeMap<String, String> map = new TreeMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier))
                continue;
            String typeStr = field.getType().getSimpleName();
            if (typeStr.equals("int")
                    || typeStr.equals("long")) {
                typeStr = "integer";
            } else if (typeStr.equals("String")) {
                typeStr = "text";
            }else if(typeStr.equals("boolean")){
                typeStr = "integer";
            }
            map.put(field.getName(), typeStr);
        }
        return map;
    }


}
