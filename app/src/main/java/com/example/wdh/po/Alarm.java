package com.example.wdh.po;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "system_alarm")
public class Alarm {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "hourOfDay" ,useGetSet = true)
    private int hourOfDay;
    @DatabaseField(columnName = "minute" ,useGetSet = true)
    private int minute;
    @DatabaseField(columnName = "isOn" ,useGetSet = true)
    private String isOn;
    @DatabaseField(columnName = "repeatDay" ,useGetSet = true)
    private String repeatDay;
    @DatabaseField(columnName = "userId",useGetSet = true)
    private String userId;
    public Alarm() {
    }

    public Alarm(int hourOfDay, int minute, String isOn, String repeatDay, String userId) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.isOn = isOn;
        this.repeatDay = repeatDay;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getIsOn() {
        return isOn;
    }

    public void setIsOn(String isOn) {
        this.isOn = isOn;
    }

    public String getRepeatDay() {
        return repeatDay;
    }

    public void setRepeatDay(String repeatDay) {
        this.repeatDay = repeatDay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", hourOfDay=" + hourOfDay +
                ", minute=" + minute +
                ", isOn='" + isOn + '\'' +
                ", repeatDay='" + repeatDay + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
