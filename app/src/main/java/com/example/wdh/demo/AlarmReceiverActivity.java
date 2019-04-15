package com.example.wdh.demo;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;

import com.example.wdh.app.MyApplication;
import com.example.wdh.util.ActivityCollector;

public class AlarmReceiverActivity extends BroadcastReceiver {

    public void onReceive(final Context context, Intent intent) {
        Log.i("clock", "闹钟响了........");

        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
        Uri uriR = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone ringtone=RingtoneManager.getRingtone(MyApplication.getContext(),uriR);
        if (ringtone != null && !ringtone.isPlaying()) {
            ringtone.play();
        }
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.RINGER_MODE_NORMAL, 5, 0);
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        dialogBuilder.setTitle("闹钟");
        dialogBuilder.setMessage("啦啦啦啦啦闹钟响啦");
        //dialogBuilder.setCancelable(false);
        dialogBuilder.setNegativeButton("再睡会", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NaozhongActivity naozhong=new NaozhongActivity();
                naozhong.setClock(100,5);
                vibrator.cancel();
                ringtone.stop();
            }
        });
        dialogBuilder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                vibrator.cancel();
                ringtone.stop();
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW+3);
        alertDialog.show();
    }
}
