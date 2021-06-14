package cn.zhangle.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent integer = getIntent();
        String name = integer.getStringExtra("name");
        String pwd = integer.getStringExtra("id");
        String sex = integer.getStringExtra("sex");
        String classname = integer.getStringExtra("class");
        String date  = integer.getStringExtra("date");
        TextView textView = findViewById(R.id.msg);
        String s = "姓名："+name +"\n"+"学号："+pwd+"\n"+"性别："+sex+"\n"+"班级："+classname+"\n"+"入学时间" + date +"\n";
        s = s.substring(0, s.length() - 1);
        textView.setText(s);
    }
}