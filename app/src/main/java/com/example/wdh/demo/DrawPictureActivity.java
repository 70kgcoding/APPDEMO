package com.example.wdh.demo;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.wdh.view.MyDrawpictureView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DrawPictureActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.button15)
    private Button colorbtn;
    @ViewInject(R.id.button16)
    private Button clearbtn;
    @ViewInject(R.id.button18)
    private Button savebtn;
    private static int[] colors;
    private MyDrawpictureView drawpictureView;
    private Path mpath;
    private static  int biaoji=0;
    static  {
        colors= new int[]{Color.BLUE, Color.RED, Color.BLACK, Color.YELLOW, Color.GRAY};
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.draw_layout);
        x.view().inject(this);
        colorbtn.setOnClickListener(this);
        clearbtn.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        drawpictureView=findViewById(R.id.mydrawview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button15:
                int num= getdifferentnum();
                biaoji=num;
                drawpictureView.getmPaint().setColor(colors[num]);
                drawpictureView.getmPath().reset();
                colorbtn.setBackgroundColor(colors[num]);
                break;
            case R.id.button16:
                drawpictureView.clearall();
                break;
            case R.id.button18:
                drawpictureView.savepicture();
                Toast.makeText(DrawPictureActivity.this,"保存图片成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public int getdifferentnum()
    {
        int num= (int) (Math.random()*5+1);
        if(num==biaoji)
            getdifferentnum();
        else if(num>=5)
            num=0;
        return num;
    }
}
