package com.gpfei.mysql;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static int flag = 1;
    private TextView tv_all;
    private EditText ed_student_id,ed_avg,ed_id,ed_name,ed_sex,ed_brith,ed_class;
    private Button btn_avg,btn_stuavg,btn_all,btn_insert;
    private ListView listView;
    private LinearLayout linearLayout;
    private static final String TAG = "MainActivity";
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            ((TextView)findViewById(R.id.tv_show)).setText((String)message.obj);
            String str = "查询不存在";
            if(message.what == 1) str = "查询成功";
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            return false;
        }
    });

    Handler handlerstudent = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Map<String,String>> data = new ArrayList<>();
            List<Student> mp = (List<Student>) msg.obj;
            for (int i=0 ;i < mp.size();i++){
                Map<String,String> map = new HashMap<>();
                map.put("student_id",mp.get(i).gets_id());
                map.put("student_name",mp.get(i).getS_name());
                map.put("student_sex",mp.get(i).getS_sex());
                map.put("student_birthday",mp.get(i).getS_birth());
                map.put("student_class",mp.get(i).getS_class());
                data.add(map);
            }
            listView.setAdapter(new Student_Adapter(data,MainActivity.this));
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_avg = findViewById(R.id.ed_avg);
        tv_all = findViewById(R.id.tv_all);
        ed_student_id = findViewById(R.id.ed_student_id);
        ed_id = findViewById(R.id.ed_id);
        ed_name = findViewById(R.id.ed_name);
        ed_sex = findViewById(R.id.ed_sex);
        ed_brith = findViewById(R.id.ed_brith);
        ed_class = findViewById(R.id.ed_class);
        btn_avg = findViewById(R.id.btn_avg);
        btn_stuavg = findViewById(R.id.btn_stuavg);
        btn_all = findViewById(R.id.btn_all);
        btn_insert = findViewById(R.id.btn_insert);//插入成绩
        listView = findViewById(R.id.listview);
        linearLayout = findViewById(R.id.include_layout);
        setListeners();
    }

    private void setListeners() {
        OnClick onClick = new OnClick();
        btn_insert.setOnClickListener(onClick);
        btn_avg.setOnClickListener(onClick);
        btn_stuavg.setOnClickListener(onClick);
        btn_all.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_all://学生所有信息
                    all();
                    break;
                case R.id.btn_stuavg://学生平均成绩
                    stuavg();
                    break;
                case R.id.btn_avg://课程平均成绩
                    avg();
                    break;
                case R.id.btn_insert://插入数据
                    insert();
                    break;
            }
        }
    }
    private void all(){//输出所有学生信息
        linearLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Student> mp = DBUtils.getAll();
                Message msg = new Message();
                if(mp == null) {
                    msg.what = 0;
                    msg.obj =  "查询结果，空空如也";
                    //非UI线程不要试着去操作界面
                }
                else {
                    msg.what = 1;
                    msg.obj = mp;
                }
                handlerstudent.sendMessage(msg);
            }
        }).start();
    }
    private void insert() {//插入数据
        final String id = ed_id.getText().toString().trim();
        final String name = ed_name.getText().toString().trim();
        final String sex = ed_sex.getText().toString().trim();
        final String brith = ed_brith.getText().toString().trim();
        final String s_class = ed_class.getText().toString().trim();
        if (id==null||id.equals("")||name==null||name.equals("")
                ||sex ==null||sex.equals("")||brith==null||brith.equals("")||s_class==null||s_class.equals("")){
            Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else {
            Log.e("MainActivity","id是："+id);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DBUtils.insertData(id,name,sex,brith,s_class);
                }
            }).start();
        }
    }
    private void avg() {//输出某个学生的平均成绩
        linearLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        final String c_id = ed_avg.getText().toString().trim();
        Log.e(TAG,c_id);
        if (c_id==null||c_id.equals("")){
            Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Score> mp =
                            DBUtils.getUserInfoByCid(c_id);
                    Message msg = new Message();
                    if (mp == null) {
                        msg.what = 0;
                        msg.obj = "查询结果，空空如也";
                        //非UI线程不要试着去操作界面
                    } else {
                        String ss = new String();
                        double a =0;
                        for (int i = 0;i<mp.size();i++) {
                            try {
                                a = a+Double.parseDouble(mp.get(i).toString());//将string转换成double进行计算
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        a = a/mp.size();
                        ss = String.valueOf(a);//计算值转换成string输出
                        msg.what = 1;
                        msg.obj ="该课程的平均成绩:"+ ss;
                    }
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }
    private void stuavg() {//输出某个学生的平均成绩
        linearLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        final String s_id = ed_student_id.getText().toString().trim();
        Log.e(TAG, s_id);
        if (s_id==null||s_id.equals("")){
            Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Score> mp =
                            DBUtils.getUserInfoByid(s_id);
                    Message msg = new Message();
                    if(mp == null) {
                        msg.what = 0;
                        msg.obj =  "查询结果，空空如也";
                        //非UI线程不要试着去操作界面
                    }
                    else {
                        String ss = new String();
                        String name = null;
                        double a =0;
                        for (int i = 0;i<mp.size();i++) {
                            name = mp.get(i).getS_name();//拿到姓名
                            try {
                                a = a+Double.parseDouble(mp.get(i).toString());//将string转换成double进行计算
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        a = a/mp.size();
                        ss =name+"的平均成绩:"+String.valueOf(a);//计算值转换成string输出
                        // ss = name +"的平均成绩:"+ss;
                        msg.what = 1;
                        msg.obj = ss;
                    }
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }
}



