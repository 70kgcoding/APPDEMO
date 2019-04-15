package com.example.wdh.demo;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wdh.app.MyApplication;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO:todo test 1
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int CHANGE=1;

    @ViewInject(R.id.button1)
    private Button button1;

    @ViewInject(R.id.button)
    private Button button;

    private Intent intent;

    @ViewInject(R.id.button2)
    private Button notificationButton;

    @ViewInject(R.id.button4)
    private Button uploadPicture;

    @ViewInject(R.id.alarmButton)
    private Button alarmButton;

    @ViewInject(R.id.textView)
    private TextView textView;

    @ViewInject(R.id.button9)
    private Button drawpicturebtn;

    @ViewInject(R.id.button8)
    private Button scrollViewTestbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        button1.setOnClickListener(this);

        button.setOnClickListener(this);

        notificationButton.setOnClickListener(this);

        uploadPicture.setOnClickListener(this);

        alarmButton.setOnClickListener(this);

        scrollViewTestbtn.setOnClickListener(this);

        drawpicturebtn.setOnClickListener(this);

        PackageManager pm=getPackageManager();
        try {
            PackageInfo packageInfo= (PackageInfo) pm.getPackageInfo(MyApplication.getContext().getPackageName(),PackageManager.GET_ACTIVITIES);
            Log.i("MainActivity", "onCreate: "+packageInfo.firstInstallTime);
            Date date=new Date(packageInfo.firstInstallTime);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String msg="";
            msg+="您好 "+ MyApplication.getLoginId() +"，欢迎来到这个不知道干什么的app"+",第一次安装时间 :"+sdf.format(date);
            textView.setText(msg);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MainActivity", "getPackageInfo failed : "+e.getMessage());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button1:
                intent=new Intent("com.example.wdh.demo.ACTION_START");
                intent.addCategory("com.example.wdh.demo.LIST");
                startActivity(intent);
                break;
            case R.id.button:
                intent = new Intent("com.example.wdh.demo.FORCED_QUIT");
                sendBroadcast(intent);
                break;
            case R.id.button2:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this, 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification=new NotificationCompat.Builder(MainActivity.this,"default")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("这是一个Ticker")
                        .setContentTitle("这是通知标题")
                        .setContentText("这是通知内容")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent).build();
                manager.notify(1,notification);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=CHANGE;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.button4:
                intent=new Intent("com.example.wdh.demo.ACTION_START");
                intent.addCategory("com.example.wdh.demo.UPLOAD");
                startActivity(intent);
                break;
            case R.id.alarmButton:
                intent=new Intent(this,NaozhongActivity.class);
                startActivity(intent);
                break;
            case R.id.button8:
                intent=new Intent(this,ScrollViewActivity.class);
                startActivity(intent);
                break;
            case R.id.button9:
                intent=new Intent(this,DrawPictureActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case CHANGE:
                    textView.setText("点击通知栏里的通知到达神秘世界哦！");
                    break;
                default:
                    break;
            }
        }
    };
}
