 package cn.zhangle.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.zhangle.myapplication.R;

import static cn.zhangle.myapplication.util.StringUtils.isEmpty;


 public class MainActivity extends AppCompatActivity {
     private Button btnYes;
     private EditText inName;
     private EditText inPassword;
     private RadioGroup radioGroup;
     private Button checkdate;
     private TextView date;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         checkdate = findViewById(R.id.checkdate);
         date = findViewById(R.id.date);
         checkdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 click(v);
             }
         });
         btnYes = findViewById(R.id.btn_yes);
         btnYes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 passData();
             }
         });
     }


     public void click(View view) {
         System.out.println("点击了");
         final AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
         View contentview1 = LayoutInflater.from(this).inflate(R.layout.dialog_login, null);
         dialog1.setView(contentview1);
         dialog1.setCancelable(true);
         final AlertDialog dialog = dialog1.show();
         CalendarView calendar = (CalendarView)contentview1.findViewById(R.id.calendar);
         calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                 //显示用户选择的日期
                date.setText(year + "年" + month + "月" + dayOfMonth + "日");
             }
         });
     }

     public void passData() {
         inName = (EditText) findViewById(R.id.in_name);
         inPassword = (EditText) findViewById(R.id.in_password);
         this.radioGroup= findViewById(R.id.sex);
         RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
         //这里是获取各个tab

         Spinner spinner = findViewById(R.id.degree);
         String name = inName.getText().toString().trim();
         String id = inPassword.getText().toString().trim();
         String date1 = date.getText().toString().trim();
         if (isEmpty(name)){
//             Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
             tipDialog("姓名不可为空");
             return;
         }
         if (isEmpty(id) || id.length()<10){
//             Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
             tipDialog("学号不可为空且长度大于10");
             return;
         }
         Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
         intent.putExtra("name",name);
         intent.putExtra("id",id);
         intent.putExtra("sex",radioButton.getText());
         intent.putExtra("class",spinner.getSelectedItem().toString());
         intent.putExtra("date",date1);
         startActivity(intent);
     }

     public void tipDialog(String msg) {
         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
         builder.setTitle("警告：");
         builder.setMessage(msg);
         builder.setIcon(R.mipmap.ic_launcher);
         builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失

         //设置正面按钮
         builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }});
         AlertDialog dialog = builder.create();
         dialog.show();
     }
 }