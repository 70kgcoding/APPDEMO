package com.example.wdh.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wdh.app.MyApplication;
import com.example.wdh.dao.UserDao;
import com.example.wdh.po.User;

public class IndexActivity extends BaseActivity {
    private Button button;
    private Button register;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index_layout);
        button=findViewById(R.id.button_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = findViewById(R.id.editText);
                password = findViewById(R.id.editText2);
                UserDao userDao = new UserDao(IndexActivity.this);
                User user = userDao.queryByName(username.getText().toString());
                if(user!=null)
                {
                    if(user.getPassword().equals(password.getText().toString()))
                    {
                        MyApplication.setLoginId(username.getText().toString());
                        Intent intent=new Intent("com.example.wdh.demo.ACTION_START");
                        intent.addCategory("com.example.wdh.demo.DIERYE");
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(IndexActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        password.getText().clear();
                    }
                }
                else
                {
                    Toast.makeText(IndexActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                    username.getText().clear();
                    password.getText().clear();
                }

            }


        });

        register=findViewById(R.id.button5);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.wdh.demo.ACTION_START");
                intent.addCategory("com.example.wdh.demo.REGISTER");
                startActivity(intent);
            }
        });

    }


}
