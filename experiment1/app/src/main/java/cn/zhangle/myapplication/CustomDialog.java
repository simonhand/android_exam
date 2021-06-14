package cn.zhangle.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.dialog_login,null);
        setContentView(view);
        super.onCreate(savedInstanceState);
    }



}
