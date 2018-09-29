package com.lidiwo.parcelable.core;

import android.os.Parcelable;

import com.lidiwo.parcelable.constant.Consts;

import java.lang.reflect.Constructor;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 11:37
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class ParcelableManager {


    /**
     * 将有Parcelable注解的Bean对象转换成Parcelable对象
     *
     * @param obj 需要Parcelable序列化对象
     * @return Parcelable对象
     */
    public static Parcelable createParcelable(Object obj) {
        if (obj instanceof Parcelable) {
            return (Parcelable) obj;
        } else {
            if (null == obj) {
                return null;
            } else {
                try {
                    //获取当前对象的名字
                    String simpleName = obj.getClass().getSimpleName();

                    //拼接apt生成的对象名字
                    String className = simpleName.concat(Consts.NAME_OF_SUFFIX);

                    //获取apt生成对象实例
                    Class<?> clazz = Class.forName(Consts.PACKAGE_OF_GENERATE_FILE + "." + className);

                    //获取参数是注解bean类型的构造函数
                    Constructor constructor = clazz.getConstructor(obj.getClass());

                    //创建对象实例并返回
                    return (Parcelable) constructor.newInstance(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
