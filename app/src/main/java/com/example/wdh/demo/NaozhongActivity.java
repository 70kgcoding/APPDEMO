package com.example.wdh.demo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wdh.app.MyApplication;
import com.example.wdh.dao.AlarmDao;
import com.example.wdh.po.Alarm;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

public class NaozhongActivity extends BaseActivity {
    @ViewInject(R.id.switch2)
    private Switch switchbutton;
    @ViewInject(R.id.tp_custom)
    private TimePicker timePicker;
    private Alarm alarm=new Alarm();
    private AlarmDao alarmDao=new AlarmDao(NaozhongActivity.this);
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        x.view().inject(this);
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarm.setHourOfDay(hourOfDay);
                alarm.setMinute(minute);
            }
        });
        switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    alarm.setIsOn("1");
                    alarm.setUserId(MyApplication.getLoginId());
                    try
                    {
                        if (alarm != null) {
                            alarmDao.insert(alarm);
                            setClock(alarm.getHourOfDay(),alarm.getMinute());
                        }
                    }catch (Exception e)
                    {
                        Log.e("NaozhongActivity", "alarm insert failed :"+e.getMessage() );
                    }
                    Toast.makeText(NaozhongActivity.this,"闹钟设置成功，暂时可能没用哟",Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent=new Intent(NaozhongActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).start();

                }
                else
                    alarm.setIsOn("0");
            }
        });


    }
    public void setClock(int hourOfDay,int minute)
    {
        Calendar c=Calendar.getInstance();//获取日期对象
        c.setTimeInMillis(System.currentTimeMillis());        //设置Calendar对象
        if(hourOfDay==100)
        {}
        else
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);//设置闹钟小时数
        c.set(Calendar.MINUTE, minute);            //设置闹钟的分钟数
        c.set(Calendar.SECOND, 0);                //设置闹钟的秒数
        c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
        Intent intent=new Intent(NaozhongActivity.this, AlarmReceiverActivity.class);
        PendingIntent pi = PendingIntent.getBroadcast(NaozhongActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);        //设置闹钟，当前时间就唤醒
    }

}
