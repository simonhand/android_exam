package cn.zhangle.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import cn.zhangle.myapplication.utils.StringUtils;


public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberpass;
    private SharedPreferences pre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pre =  getSharedPreferences("data", MODE_PRIVATE);
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        rememberpass = findViewById(R.id.remember_password);

        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolean isRemember = pref.getBoolean("remember_password", false);
                if(isRemember){
                String account = pre.getString("name", "");
                String password = pre.getString("password", "");
                if (account.equals(etAccount.getText().toString())){
                    etPwd.setText(password);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account,pwd);
            }
        });
        boolean isRemember = pref.getBoolean("remember_password", false);

        if(isRemember){
            //将账号和密码都设置在文本框内
            String account = pre.getString("name", "");
            String password = pre.getString("password", "");
            etAccount.setText(account);
            etPwd.setText(password);
//            showToast("记住密码了");
            rememberpass.setChecked(true);
        }
    }

    private void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入用户名");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }

        if (account.equals(pre.getString("name", "")) && pwd.equals(pre.getString("password", ""))) {
            editor = pref.edit();
            if (rememberpass.isChecked()) {
                editor.putBoolean("remember_password", true);
            }
            else{
                editor.clear();
            }
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, Success.class);
            startActivity(intent);
            finish();
        } else {
            showToast("用户名或密码错误");
        }
    }

}