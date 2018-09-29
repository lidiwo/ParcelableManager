package com.lidiwo.parcelablemanagerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.lidiwo.parcelable.core.ParcelableManager;
import com.lidiwo.parcelablemanagerdemo.bean.StudentBean;
import com.lidiwo.parcelablemanagerdemo.bean.TeacherBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void skip(View view){
        StudentBean bean = getBean();
        Intent intent=new Intent(this,OneActivity.class);
        intent.putExtra("bean", ParcelableManager.createParcelable(bean));
        startActivity(intent);
    }

    private StudentBean getBean(){
        byte a=2;
        short b=7;
        Byte c=11;
        StudentBean bean=new StudentBean();
        bean.setA(1);
        bean.setB(a);
        bean.setC(3);
        bean.setD(4);
        bean.setE(5);
        bean.setF(true);
        bean.setG("@#$");
        bean.setH(6);
        bean.setJ(b);
        bean.setK(8l);
        bean.setL(9f);
        bean.setM(10D);
        bean.setN(true);
        bean.setO("老王");
        bean.setX(c);

        List<TeacherBean> lists=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            TeacherBean teacherBean=new TeacherBean();
            teacherBean.setName("老王"+i);
            lists.add(teacherBean);
        }
        bean.setP(lists);

        Map<String,TeacherBean> map=new HashMap<>();
        for (int i = 0; i <5 ; i++) {
            TeacherBean teacherBean=new TeacherBean();
            teacherBean.setName("老王@"+i);
            map.put("@@@"+i,teacherBean);
        }
        bean.setI(map);

        boolean[] booleanArray={true,false,true};
        bean.setR(booleanArray);

        byte[] byteArray={1,2,3,4,5};
        bean.setS(byteArray);

        String[] stringArray={"小王","老王","王王"};
        bean.setT(stringArray);

        CharSequence[] charSequenceArray={"小王@","老王@","王王@"};
        bean.setU(charSequenceArray);

        int[] intArray={6,7,8,9,10};
        bean.setV(intArray);

        long[] longArray={11,12,13,14,15};
        bean.setW(longArray);

        double[] doubleArray={16,17,18,19,20};
        bean.setY(doubleArray);

        SparseArray<TeacherBean> sparseArray=new SparseArray<>();
        for (int i = 0; i <5 ; i++) {
            TeacherBean teacherBean=new TeacherBean();
            teacherBean.setName("小张"+i);
            sparseArray.put(i,teacherBean);
        }
        bean.setQ(sparseArray);

        return bean;
    }
}