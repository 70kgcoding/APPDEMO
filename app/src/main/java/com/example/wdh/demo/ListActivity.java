package com.example.wdh.demo;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wdh.po.Msg;
import com.example.wdh.util.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;

    private List<Msg> msgList = new ArrayList<Msg>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_layout);
        initMsgs(); // 初始化消息数据
        adapter = new MsgAdapter(ListActivity.this, R.layout.msg_item, msgList);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgListView =findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);  //设置适配器
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged(); // 当有新消息时，刷新ListView中的显示
                    msgListView.setSelection(msgList.size()); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                    if(msg.getContent().equals("1"))
                    {
                        Msg msg1 = new Msg("这是问题一的答案" , Msg.TYPE_RECEIVED);
                        msgList.add(msg1);
                    }
                }
            }
        });
    }
    private void initMsgs() {
        Msg msg1 = new Msg("您好，我是智能客服001" , Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }

}
