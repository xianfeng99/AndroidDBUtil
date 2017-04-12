package com.xfeng.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lixianfeng on 2017/4/10.
 */

public class DataBaseUtil {

    public static DataBaseUtil mInstance;
    private MyDatabaseHelper mHelper;

    private DataBaseUtil(Context context){
        mHelper = new MyDatabaseHelper(context.getApplicationContext());
    }

    public static DataBaseUtil getInstance(Context context){
        if(mInstance == null){
            mInstance = new DataBaseUtil(context);
        }
        return mInstance;
    }

    /**
     * 将ContentBean加入到数据库
     * @param bean
     */
    public void insert(UserBean bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            ContentValues values = getFieldAndValueMap(bean);
            db.insert(UserBean.class.getSimpleName(), null, values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除浏览历史
     * @param bean
     */
    public void delete(UserBean bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            //先判断数据库中是否已经有该条数据，如果有则更新，没有则插入
            db.delete(UserBean.class.getSimpleName(), null, null);

        } catch (Exception e) {
        }
    }

    public void deleteList(ArrayList<UserBean> list){

        SQLiteDatabase db = mHelper.getWritableDatabase();

        for(UserBean bean : list){
            db.delete(UserBean.class.getSimpleName(), null, null);
        }
    }

    /**
     * 获取给定类型（已经创建了该类对应到表）的数据库记录
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public ArrayList getList(Class clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        ArrayList list = new ArrayList();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ArrayList<String> fieldlist = ClassUtil.getDataBaseFieldStrings(clazz);
        String[] projection = new String[fieldlist.size()];
        for(int i = 0; i < fieldlist.size(); i ++){
            projection[i] = fieldlist.get(i);
        }

        Cursor cursor = db.query(clazz.getSimpleName(), projection, null, null, null, null, null);
        boolean hasCursor = cursor.moveToFirst();
        while(hasCursor && cursor != null){

            Object object = clazz.newInstance();
            int index = 0;
            String typeStr = "";
            for(String columnName : projection){
                index = cursor.getColumnIndex(columnName);
                Field field = object.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                typeStr = field.getType().getSimpleName();
                if(typeStr.equals("int")){
                    field.set(object, cursor.getInt(index));
                }else if(typeStr.equals("long")){
                    field.set(object, cursor.getLong(index));
                }else if(typeStr.equals("String")){
                    field.set(object, cursor.getString(index));
                }
            }
            list.add(object);
            if(!cursor.moveToNext()){
                break;
            }
        }
        return list;
    }

    /**
     * 将对应到属性和值存储到数据库
     * @param obj
     * @return
     */
    public ContentValues getFieldAndValueMap(Object obj) throws IllegalAccessException {

        ContentValues values = new ContentValues();
        Field[]  fields = obj.getClass().getDeclaredFields();
        for(Field field: fields){
            int modifier = field.getModifiers();
            if(Modifier.isFinal(modifier) || Modifier.isStatic(modifier))
                continue;
            String typeStr = field.getType().getSimpleName();
            field.setAccessible(true);
            if(typeStr.equals("int")){
                values.put(field.getName(), (int)field.getInt(obj));
            }else if(typeStr.equals("long")){
                values.put(field.getName(), (long)field.getLong(obj));
            }else if(typeStr.equals("String")){
                values.put(field.getName(), (String)field.get(obj));
            }
        }
        return values;
    }

    /**
     * 创建对应类的数据库表(目前只支持整型和字符串类型)
     * @param clazz
     * @return
     */
    public static String getCreateTable(Class clazz) {

        Map<String, String> map = ClassUtil.getDataBaseMap(clazz);
        int size = map.size();
        Iterator iterator = map.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        String per = "Create table if not exists " + clazz.getSimpleName() + " ( ";
        sb.append(per);
        sb.append("_id integer primay key,");
        for(int i = 0; i < size; i ++){
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey() + " " + entry.getValue());
            if(i == size - 1)
                break;
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }
}
