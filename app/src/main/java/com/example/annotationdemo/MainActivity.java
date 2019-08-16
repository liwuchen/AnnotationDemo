package com.example.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.annotationdemo.annotation.BindClick;
import com.example.annotationdemo.annotation.BindId;
import com.example.annotationdemo.annotation.BindView;

import java.lang.reflect.Field;

@BindClick(ids={R.id.btn_hello1, R.id.btnHello2})
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindId(id=R.id.btn_hello1)
    Button btnHello1;

    @BindView
    Button btnHello2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseBindView();
        parseBindId();
        parseBindClick();
        btnHello1.setText("@BindId示例（需要指定Id）");
        btnHello2.setText("@BindView示例（不需要指定Id）");
    }

    /**
     * 绑定view（代码中控件的变量名就是布局文件中该控件的id名）
     */
    private void parseBindView() {
        Class<?> clazz = this.getClass();
        Field[] fields =clazz.getDeclaredFields();
        /* 解析父类的注解
        while (!clazz.getName().equals("androidx.appcompat.app.AppCompatActivity")) {
            clazz = clazz.getSuperclass();
            fields = concat(fields, clazz.getDeclaredFields());
        }
        */

        if (fields == null) {
            return;
        }
        Resources resources = getResources();
        String packageName = getPackageName();
        for(Field field : fields) {
            if (field.isAnnotationPresent(BindView.class)) {
                int id = resources.getIdentifier(field.getName(), "id", packageName);
                if (id > 0) {
                    View view = findViewById(id);
                    field.setAccessible(true);
                    try {
                        field.set(this, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 绑定view（代码中控件的变量名可以自己起）
     */
    private void parseBindId() {
        Class<?> clazz = this.getClass();
        Field[] fields =clazz.getDeclaredFields();
        /* 解析父类的注解
        while (!clazz.getName().equals("androidx.appcompat.app.AppCompatActivity")) {
            clazz = clazz.getSuperclass();
            fields = concat(fields, clazz.getDeclaredFields());
        }
        */

        if (fields == null) {
            return;
        }
        for(Field field : fields) {
            if (field.isAnnotationPresent(BindId.class)) {
                BindId bindId = field.getAnnotation(BindId.class);
                field.setAccessible(true);
                try {
                    View view = findViewById(bindId.id());
                    field.set(this, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Field[] concat(Field[] a, Field[] b) {
        Field[] c= new Field[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }


    /**
     * 解析BindClick注解，给使用注解的控件设置点击监听
     */
    private void parseBindClick() {
        BindClick bindClick = getClass().getAnnotation(BindClick.class);
        if (bindClick != null) {
            int[] ids = bindClick.ids();
            for (int id : ids) {
                findViewById(id).setOnClickListener(this);
            }
        }
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
