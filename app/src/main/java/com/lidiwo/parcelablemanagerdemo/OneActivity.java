package com.lidiwo.parcelablemanagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lidiwo.parcelablemanagerdemo.bean.StudentBean;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/28 14:46
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class OneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        TextView tv_content = findViewById(R.id.tv_content);

        StudentBean bean=  getIntent().getParcelableExtra("bean");
        if(bean!=null){
            tv_content.setText(bean.toString());
        }else{
            tv_content.setText("null");
        }
    }
}
