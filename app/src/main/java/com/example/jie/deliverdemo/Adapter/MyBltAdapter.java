package com.example.jie.deliverdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jie.deliverdemo.Moudle.ReadAllBlt;
import com.example.jie.deliverdemo.R;

import java.util.List;

/**
 * Created by lcj on 2018/5/31.
 */

public class MyBltAdapter extends BaseAdapter {

    private List<ReadAllBlt> lists;
    private Context context;
    private ViewHolder holder;

    public MyBltAdapter(Context context, List<ReadAllBlt> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int p=position;
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_my_blt, null);
            holder.BltAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.BltName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.BltAddress.setText(lists.get(position).getAddress());
        holder.BltName.setText(lists.get(position).getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                context.startActivity(i);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView BltAddress;
        TextView BltName;
        LinearLayout linearLayout;
    }

}
