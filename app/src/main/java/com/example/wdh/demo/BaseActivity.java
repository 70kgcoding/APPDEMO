package com.example.wdh.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wdh.util.ActivityCollector;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_item:
                Intent intent = new Intent("com.example.wdh.demo.ACTION_START");
                intent.addCategory("com.example.wdh.demo.ABOUT");
                startActivity(intent);
                break;
            case R.id.remove_item:
                Toast.makeText(this,"please 拨打 110",Toast.LENGTH_SHORT).show();
                break;
            case R.id.quit_item:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return true;
    }

}
