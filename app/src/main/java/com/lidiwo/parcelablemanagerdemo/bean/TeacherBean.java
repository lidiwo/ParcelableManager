package com.lidiwo.parcelablemanagerdemo.bean;


import com.lidiwo.parcelable.annotation.Parcelable;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 10:48
 * @Company：智能程序员
 * @Description： *****************************************************
 */
@Parcelable
public class TeacherBean{

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeacherBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
