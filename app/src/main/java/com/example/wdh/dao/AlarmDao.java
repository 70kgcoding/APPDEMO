package com.example.wdh.dao;


import android.content.Context;

import com.example.wdh.po.Alarm;
import com.example.wdh.util.MyDatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class AlarmDao  {
    private Context context;
    private Dao<Alarm, Integer> dao;

    public AlarmDao(Context context)
    {
        try {
            this.dao=MyDatabaseHelper.getDatabaseHelper(context).getDao(Alarm.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向Alarm表中添加一条数据
    public void insert(Alarm data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除Alarm表中的一条数据
    public void delete(Alarm data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改Alarm表中的一条数据
    public void update(Alarm data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询Alarm表中的所有数据
    public List<Alarm> selectAll() {
        List<Alarm> alarms = null;
        try {
            alarms = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    // 根据ID取出闹钟信息
    public Alarm queryById(int id) {
        Alarm alarm = null;
        try {
            alarm = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarm;
    }
}
