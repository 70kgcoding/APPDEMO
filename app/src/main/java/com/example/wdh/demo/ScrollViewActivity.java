package com.example.wdh.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class ScrollViewActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.scrollview100)
    private ScrollView scrollView;
    @ViewInject(R.id.button6)
    private Button buttondi;
    @ViewInject(R.id.button7)
    private Button buttonding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_layout);
        x.view().inject(this);
        buttondi.setOnClickListener(this);
        buttonding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button6:
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                break;
            case R.id.button7:
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                break;
            default:
                break;
        }
    }
}
