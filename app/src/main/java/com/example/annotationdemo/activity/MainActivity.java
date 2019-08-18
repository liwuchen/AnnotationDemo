package com.example.annotationdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.annotationdemo.R;
import com.example.annotationdemo.annotation.BindClick;
import com.example.annotationdemo.annotation.BindId;
import com.example.annotationdemo.annotation.BindView;

import java.lang.reflect.Field;

@BindClick(ids={R.id.btn_hello1, R.id.btnHello2})
public class MainActivity extends BaseActivity {

    @BindId(id=R.id.btn_hello1)
    Button btnHello1;

    @BindView
    Button btnHello2;

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    void initView() {
        btnHello1.setText("@BindId示例（需要指定Id）");
        btnHello2.setText("@BindView示例（不需要指定Id）");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hello1:
                Toast.makeText(this, "点击了按钮1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnHello2:
                Toast.makeText(this, "点击了按钮2", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
