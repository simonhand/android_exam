package cn.zhangle.myapplication.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Classname BaseUtils
 * @Description TODO
 * @Date 2021/4/18 18:42
 * @Created by zl363
 * 仙人保佑，阿乐代码永无bug
 */
public class BaseUtils  extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext  = this;

    }
    public void showToastSyn(String msg){//这样ui进程
        Looper.prepare();
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
