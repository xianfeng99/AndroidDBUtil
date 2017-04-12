# AndroidDBUtil
使用非常简单(Very simple to use)<br>
<br>
第一步（1 step）<br>
在你的SQLiteOpenHelper中创建你需要对应类的表<br>
```java
    @Override
    public void onCreate(SQLiteDatabase db) {
        //1.创建表，可以创建多个表（create tables）
        db.execSQL(DataBaseUtil.getCreateTable(UserBean.class));
    }
```
<br>
第二步(2 step)<br>
    //2.添加实体对象到数据库(add your object into database)<br>
        DataBaseUtil.getInstance(this).insert(userBean);
<br>
第三步(3 step)<br>
    //3.获取对应实体类到数据库数据(get datas from database)<br>
        ArrayList list = DataBaseUtil.getInstance(this).getList(UserBean.class);
<br>
上面需要try...catch异常（add try...catch some Exceptions）
