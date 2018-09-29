package com.lidiwo.parcelable.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 11:44
 * @Company：智能程序员
 * @Description： *****************************************************
 */
@Target(ElementType.TYPE)//只能修饰类，接口，枚举
@Retention(RetentionPolicy.CLASS)//作用于字节码文件中
public @interface Parcelable {

}
