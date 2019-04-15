package com.example.wdh.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.wdh.po.Alarm;
import com.example.wdh.po.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库操作管理工具类
 *
 * 我们需要自定义一个类继承自ORMlite给我们提供的OrmLiteSqliteOpenHelper，创建一个构造方法，重写两个方法onCreate()和onUpgrade()
 * 在onCreate()方法中使用TableUtils类中的createTable()方法初始化数据表
 * 在onUpgrade()方法中我们可以先删除所有表，然后调用onCreate()方法中的代码重新创建表
 *
 * 我们需要对这个类进行单例，保证整个APP中只有一个SQLite Connection对象
 *
 * 这个类通过一个Map集合来管理APP中所有的DAO，只有当第一次调用这个DAO类时才会创建这个对象（并存入Map集合中）
 * 其他时候都是直接根据实体类的路径从Map集合中取出DAO对象直接调用
 */
public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "mydemo.db";

    public static final int DB_VERSION = 1; // 版本号  更新时修改这个数值

    private static MyDatabaseHelper databaseHelper;
    // 私有的构造方法
    private MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    synchronized
    public static MyDatabaseHelper getDatabaseHelper(Context context)
    {
        if(databaseHelper==null)
        {
            synchronized (MyDatabaseHelper.class)
            {
                if(databaseHelper==null)
                {
                    databaseHelper = new MyDatabaseHelper(context);
                }
            }
        }
        return  databaseHelper;
    }

    Class<?> classes[]=new Class<?>[]{
            User.class,
            Alarm.class
    };

    // 存储APP中所有的DAO对象的Map集合
    private Map<String, Dao> daos = new HashMap<>();

    // 根据传入的DAO的路径获取到这个DAO的单例对象（要么从daos这个Map中获取，要么新创建一个并存入daos）
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for(int i=0;i<classes.length;i++)
            {
                TableUtils.createTable(connectionSource,classes[i]);
            }
        } catch (SQLException e) {
            Log.e("MyDatabaseHelper", "OnCreate : "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                for(int i=0;i<classes.length;i++) {
                    TableUtils.dropTable(connectionSource, classes[i], true);
                }
                onCreate(database,connectionSource);
            } catch (SQLException e) {
                Log.e("MyDatabaseHelper", "onUpgrade : "+e.getMessage());
            }
    }


    // 释放资源
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
