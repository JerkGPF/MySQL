package com.gpfei.mysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class Student_Adapter extends BaseAdapter {
    List<Map<String,String>> map ;
    Context context;

    public Student_Adapter(List<Map<String, String>> map, Context context) {
        this.map = map;
        this.context = context;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.student_list,null);
            viewHolder.student_id = convertView.findViewById(R.id.student_id);
            viewHolder.student_name = convertView.findViewById(R.id.student_name);
            viewHolder.student_sex = convertView.findViewById(R.id.student_sex);
            viewHolder.student_birthday = convertView.findViewById(R.id.student_birthday);
            viewHolder.student_class = convertView.findViewById(R.id.student_class);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.student_id.setText(map.get(position).get("student_id"));
        viewHolder.student_name.setText(map.get(position).get("student_name"));
        viewHolder.student_sex.setText(map.get(position).get("student_sex"));
        viewHolder.student_birthday.setText(map.get(position).get("student_birthday"));
        viewHolder.student_class.setText(map.get(position).get("student_class"));
        return convertView;
    }

    private class ViewHolder{
        TextView student_id;
        TextView student_name;
        TextView student_sex;
        TextView student_birthday;
        TextView student_class;
    }
}
