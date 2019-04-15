package com.example.wdh.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wdh.dao.UserDao;
import com.example.wdh.po.User;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    // 注册按钮
    private  Button button;
    private EditText username;
    private EditText password;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        button=findViewById(R.id.button3);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        username=findViewById(R.id.editText3);
        password=findViewById(R.id.editText4);
        EditText another = findViewById(R.id.editText5);
        email=findViewById(R.id.editText6);
        if(password.getText().toString().equals(another.getText().toString()))
        {
            user=new User(username.getText().toString(),password.getText().toString(),email.getText().toString());
            UserDao userDao=new UserDao(RegisterActivity.this);
            userDao.insert(user);
            Intent intent = new Intent(this,IndexActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
            password.getText().clear();
            another.getText().clear();
        }
    }
}
