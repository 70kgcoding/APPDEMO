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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.wdh.app.MyApplication;
import com.example.wdh.po.Icon;
import com.example.wdh.util.MyBaseAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// TODO:todo test 1
public class MainActivity extends BaseActivity{
    private final int CHANGE=1;

    private Intent intent;


    @ViewInject(R.id.textView)
    private TextView textView;

    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

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


        grid_photo = (GridView) findViewById(R.id.grid_photo);

        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.ic_demo, "智能客服"));
        mData.add(new Icon(R.drawable.ic_quit, "强制退出"));
        mData.add(new Icon(R.drawable.ic_zhinanzhen, "指南针"));
        mData.add(new Icon(R.drawable.ic_picture, "图片"));
        mData.add(new Icon(R.drawable.ic_naozhong, "闹钟"));
        mData.add(new Icon(R.drawable.ic_list, "scrollview"));
        mData.add(new Icon(R.drawable.ic_huahua, "画画"));
        mData.add(new Icon(R.drawable.ic_user, "所有用户"));

        mAdapter = new MyBaseAdapter<Icon>(mData, R.layout.grid_item) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        intent=new Intent("com.example.wdh.demo.ACTION_START");
                        intent.addCategory("com.example.wdh.demo.LIST");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent("com.example.wdh.demo.FORCED_QUIT");
                        sendBroadcast(intent);
                        break;
                    case 2:
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        intent=new Intent(MainActivity.this,NotificationActivity.class);
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
                    case 3:
                        intent=new Intent("com.example.wdh.demo.ACTION_START");
                        intent.addCategory("com.example.wdh.demo.UPLOAD");
                        startActivity(intent);
                        break;
                    case 4:
                        intent=new Intent(MainActivity.this,NaozhongActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent=new Intent(MainActivity.this,ScrollViewActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent=new Intent(MainActivity.this,DrawPictureActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent=new Intent(MainActivity.this,ShowUsersActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

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
