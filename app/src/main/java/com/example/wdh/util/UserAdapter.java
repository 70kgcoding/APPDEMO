package com.example.wdh.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.wdh.demo.R;
import com.example.wdh.po.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends BaseAdapter {
    private Map<Integer,Boolean> statusmap=new HashMap<>();
    private List<User> users;
    private Context mcontext;
    public UserAdapter(List<User> users, Context mcontext) {
        this.users = users;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ResourceType")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = (View) LayoutInflater.from(mcontext).inflate(R.layout.user_item, parent, false);
            holder.usernametx = convertView.findViewById(R.id.usrenametx);
            holder.emailtx = convertView.findViewById(R.id.emailtx);
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        statusmap.put(position, true);
                    else
                        statusmap.remove(position);
                }
            });
            if (statusmap != null && statusmap.containsKey(position))
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
            holder.usernametx.setText(users.get(position).getUsername());
            holder.emailtx.setText(users.get(position).getEmail_address());


        return convertView;

    }

    public void remove(int position) {
        if(users != null) {
            users.remove(position);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView usernametx;
        TextView emailtx;
        CheckBox checkBox;
    }
}
