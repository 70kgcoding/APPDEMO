package com.example.wdh.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wdh.dao.UserDao;
import com.example.wdh.po.User;
import com.example.wdh.util.UserAdapter;

import java.util.List;

public class ShowUsersActivity extends BaseActivity {
    private UserDao userDao;
    private List<User> users;
    private UserAdapter userAdapter;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userinfo_list_layout);
        listview=findViewById(R.id.userinfo_list);
        userDao=new UserDao(ShowUsersActivity.this);
        users=userDao.selectAllNotDel();
        userAdapter=new UserAdapter(users,ShowUsersActivity.this);
        listview.setAdapter(userAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ShowUsersActivity.this).setTitle("确定删除该用户信息吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDao.deleteBySetDeleted(users.get(position).getUsername());
                        userAdapter.remove(position);
                    }
                }).setNegativeButton("取消",null).show();
                //Toast.makeText(ShowUsersActivity.this,"you are choosing the man :"+users.get(position).getUsername(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
