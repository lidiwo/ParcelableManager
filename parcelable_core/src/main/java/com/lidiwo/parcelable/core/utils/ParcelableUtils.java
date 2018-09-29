package com.lidiwo.parcelable.core.utils;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;

import com.lidiwo.parcelable.core.ParcelableManager;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 16:12
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class ParcelableUtils {
    private static final int VAL_NULL = -1;
    private static final int VAL_STRING = 0;
    private static final int VAL_INTEGER = 1;
    private static final int VAL_MAP = 2;
    private static final int VAL_BUNDLE = 3;
    private static final int VAL_PARCELABLE = 4;
    private static final int VAL_SHORT = 5;
    private static final int VAL_LONG = 6;
    private static final int VAL_FLOAT = 7;
    private static final int VAL_DOUBLE = 8;
    private static final int VAL_BOOLEAN = 9;
    private static final int VAL_CHARSEQUENCE = 10;
    private static final int VAL_LIST = 11;
    private static final int VAL_SPARSEARRAY = 12;
    private static final int VAL_BYTEARRAY = 13;
    private static final int VAL_STRINGARRAY = 14;
    private static final int VAL_IBINDER = 15;
    private static final int VAL_PARCELABLEARRAY = 16;
    private static final int VAL_OBJECTARRAY = 17;
    private static final int VAL_INTARRAY = 18;
    private static final int VAL_LONGARRAY = 19;
    private static final int VAL_BYTE = 20;
    private static final int VAL_SERIALIZABLE = 21;
    private static final int VAL_SPARSEBOOLEANARRAY = 22;
    private static final int VAL_BOOLEANARRAY = 23;
    private static final int VAL_CHARSEQUENCEARRAY = 24;
    private static final int VAL_PERSISTABLEBUNDLE = 25;
    private static final int VAL_SIZE = 26;
    private static final int VAL_SIZEF = 27;
    private static final int VAL_DOUBLEARRAY = 28;

    /**
     * 将需要序列化的bean的属性值克隆的 apt生成对象中
     *
     * @param obj           被注解需要Parcelable序列化的Bean对象
     * @param parcelableObj apt生成的已经实现Parcelable接口的bean
     */
    public static void cloneBean(Object obj, Object parcelableObj) {
        if (obj != null && parcelableObj != null) {
            Class<?> clazz = obj.getClass();
            try {
                cloneBean(clazz, obj, parcelableObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException();
        }
    }

    private static void cloneBean(Class<?> clazz, Object obj, Object parcelableObj) throws Exception {
        //获取对象中全部字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //设置所有权限修饰的字段都可以访问
            field.setAccessible(true);

            //判断字段修饰符是否有final修饰
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            //获取当前字段的值
            Object value = field.get(obj);
            //给生成对象赋值
            field.set(parcelableObj, value);
        }

        //判断当前对象有没有非Object的父类
        Class superClazz = clazz.getSuperclass();

        if (superClazz != null && !superClazz.equals(Object.class)) {
            cloneBean(superClazz, obj, parcelableObj);
        }
    }

    /**
     * 真正序列化对象
     *
     * @param parcel
     * @param flags
     * @param parcelableObj
     */
    public static void writeToParcel(Parcel parcel, int flags, Object parcelableObj) {
        try {
            writeToParcel(parcelableObj, parcelableObj.getClass().getSuperclass(), parcel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToParcel(Object obj, Class clazz, Parcel parcel) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            writeFieldValue(obj, field, parcel);
        }
        Class superClazz = clazz.getSuperclass();

        if (superClazz != null && !superClazz.equals(Object.class)) {
            writeToParcel(obj, superClazz, parcel);
        }
    }

    private static void writeFieldValue(Object obj, Field field, Parcel parcel) throws Exception {
        field.setAccessible(true);
        //判断字段的数据类型
        Class fieldClazz = field.getType();
        if (fieldClazz.equals(int.class)) {
            parcel.writeInt(field.getInt(obj));
        } else if (fieldClazz.equals(byte.class)) {
            parcel.writeByte(field.getByte(obj));
        } else if (fieldClazz.equals(float.class)) {
            parcel.writeFloat(field.getFloat(obj));
        } else if (fieldClazz.equals(double.class)) {
            parcel.writeDouble(field.getDouble(obj));
        } else if (fieldClazz.equals(long.class)) {
            parcel.writeLong(field.getLong(obj));
        } else if (fieldClazz.equals(boolean.class)) {
            parcel.writeInt(field.getBoolean(obj) ? 1 : 0);
        } else {
            Object value = field.get(obj);
            writeValue(value, parcel);
        }
    }

    private static void writeValue(Object value, Parcel parcel) {
        try {
            writeFieldValue(value, parcel);
        } catch (Exception e) {
            //处理非基本数据类型异常调用
            writeFieldValue(ParcelableManager.createParcelable(value), parcel);
        }
    }

    private static void writeFieldValue(Object value, Parcel parcel) {
        if (value == null) {
            parcel.writeInt(VAL_NULL);
        } else if (value instanceof String) {
            parcel.writeInt(VAL_STRING);
            parcel.writeString((String) value);
        } else if (value instanceof Integer) {
            parcel.writeInt(VAL_INTEGER);
            parcel.writeInt((Integer) value);
        } else if (value instanceof Map) {
            parcel.writeInt(VAL_MAP);
            writeMap((Map) value, parcel);
        } else if (value instanceof Bundle) {
            // Must be before Parcelable
            parcel.writeInt(VAL_BUNDLE);
            parcel.writeBundle((Bundle) value);
        } else if (value instanceof Parcelable) {
            // IMPOTANT: cases for classes that implement Parcelable must
            // come before the Parcelable case, so that their specific VAL_*
            // types will be written.
            parcel.writeInt(VAL_PARCELABLE);
            parcel.writeParcelable((Parcelable) value, 0);
        } else if (value instanceof Short) {
            parcel.writeInt(VAL_SHORT);
            parcel.writeInt(((Short) value).intValue());
        } else if (value instanceof Long) {
            parcel.writeInt(VAL_LONG);
            parcel.writeLong((Long) value);
        } else if (value instanceof Float) {
            parcel.writeInt(VAL_FLOAT);
            parcel.writeFloat((Float) value);
        } else if (value instanceof Double) {
            parcel.writeInt(VAL_DOUBLE);
            parcel.writeDouble((Double) value);
        } else if (value instanceof Boolean) {
            parcel.writeInt(VAL_BOOLEAN);
            parcel.writeInt((Boolean) value ? 1 : 0);
        } else if (value instanceof CharSequence) {
            // Must be after String
            parcel.writeInt(VAL_CHARSEQUENCE);
            TextUtils.writeToParcel((CharSequence) value, parcel, 0);
        } else if (value instanceof List) {
            parcel.writeInt(VAL_LIST);
            writeList(parcel, (List) value);
        } else if (value instanceof SparseArray) {
            parcel.writeInt(VAL_SPARSEARRAY);
            writeSparseArray((SparseArray) value, parcel);
        } else if (value instanceof boolean[]) {
            parcel.writeInt(VAL_BOOLEANARRAY);
            parcel.writeBooleanArray((boolean[]) value);
        } else if (value instanceof byte[]) {
            parcel.writeInt(VAL_BYTEARRAY);
            parcel.writeByteArray((byte[]) value);
        } else if (value instanceof String[]) {
            parcel.writeInt(VAL_STRINGARRAY);
            parcel.writeStringArray((String[]) value);
        } else if (value instanceof CharSequence[]) {
            // Must be after String[] and before Object[]
            parcel.writeInt(VAL_CHARSEQUENCEARRAY);
            writeCharSequenceArray((CharSequence[]) value, parcel);
        } else if (value instanceof IBinder) {
            parcel.writeInt(VAL_IBINDER);
            parcel.writeStrongBinder((IBinder) value);
        } else if (value instanceof Parcelable[]) {
            parcel.writeInt(VAL_PARCELABLEARRAY);
            parcel.writeParcelableArray((Parcelable[]) value, 0);
        } else if (value instanceof int[]) {
            parcel.writeInt(VAL_INTARRAY);
            parcel.writeIntArray((int[]) value);
        } else if (value instanceof long[]) {
            parcel.writeInt(VAL_LONGARRAY);
            parcel.writeLongArray((long[]) value);
        } else if (value instanceof Byte) {
            parcel.writeInt(VAL_BYTE);
            parcel.writeInt((Byte) value);
        } else if (value instanceof double[]) {
            parcel.writeInt(VAL_DOUBLEARRAY);
            parcel.writeDoubleArray((double[]) value);
        }else {
            Class<?> clazz = value.getClass();
            if (clazz.isArray() && clazz.getComponentType() == Object.class) {
                // Only pure Object[] are written here, Other arrays of non-primitive types are
                // handled by serialization as this does not record the component type.
                parcel.writeInt(VAL_OBJECTARRAY);
                writeArray((Object[]) value, parcel);
            } else if (value instanceof Serializable) {
                // Must be last
                parcel.writeInt(VAL_SERIALIZABLE);
                parcel.writeSerializable((Serializable) value);
            } else {
                throw new RuntimeException("Parcel: unable to marshal value " + value);
            }
        }
    }

    /**
     * Map
     *
     * @param val
     * @param parcel
     */
    private static void writeMap(Map val, Parcel parcel) {
        writeMapInternal((Map<String, Object>) val, parcel);
    }

    private static void writeMapInternal(Map<String, Object> val, Parcel parcel) {
        if (val == null) {
            parcel.writeInt(VAL_NULL);
            return;
        }
        Set<Map.Entry<String, Object>> entries = val.entrySet();
        parcel.writeInt(entries.size());
        for (Map.Entry<String, Object> e : entries) {
            writeValue(e.getKey(), parcel);
            writeValue(e.getValue(), parcel);
        }
    }

    /**
     * List 集合泛型是对象时候
     *
     * @param parcel
     * @param val
     */
    private static void writeList(Parcel parcel, List val) {
        if (val == null) {
            parcel.writeInt(VAL_NULL);
            return;
        }
        int N = val.size();
        int i = 0;
        parcel.writeInt(N);
        while (i < N) {
            writeValue(val.get(i), parcel);
            i++;
        }
    }

    private static void writeSparseArray(SparseArray<Object> val, Parcel parcel) {
        if (val == null) {
            parcel.writeInt(VAL_NULL);
            return;
        }
        int N = val.size();
        parcel.writeInt(N);
        int i = 0;
        while (i < N) {
            parcel.writeInt(val.keyAt(i));
            writeValue(val.valueAt(i), parcel);
            i++;
        }
    }

    private static void writeCharSequenceArray(CharSequence[] val, Parcel parcel) {
        if (val != null) {
            int N = val.length;
            parcel.writeInt(N);
            for (int i = 0; i < N; i++) {
                TextUtils.writeToParcel(val[i], parcel, 0);
            }
        } else {
            parcel.writeInt(VAL_NULL);
        }
    }


    public static void writeArray(Object[] val, Parcel parcel) {
        if (val == null) {
            parcel.writeInt(VAL_NULL);
            return;
        }
        int N = val.length;
        int i = 0;
        parcel.writeInt(N);
        while (i < N) {
            writeValue(val[i], parcel);
            i++;
        }
    }

    public static void readFromParcel(Parcel parcel, Object parcelableObj) {
        try {
            readFromParcel(parcelableObj, parcelableObj.getClass().getSuperclass(), parcel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFromParcel(Object obj, Class clazz, Parcel parcel) throws Exception {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            readFieldValue(obj, field, parcel);
        }
        Class superClazz = clazz.getSuperclass();

        if (superClazz != null && !superClazz.equals(Object.class)) {
            readFromParcel(obj, superClazz, parcel);
        }
    }

    private static void readFieldValue(Object obj, Field field, Parcel parcel) throws Exception {
        field.setAccessible(true);
        //判断字段的数据类型
        Class fieldClazz = field.getType();
        if (fieldClazz.equals(int.class)) {
            field.setInt(obj, parcel.readInt());
        } else if (fieldClazz.equals(byte.class)) {
            field.setByte(obj, parcel.readByte());
        } else if (fieldClazz.equals(float.class)) {
            field.setFloat(obj, parcel.readFloat());
        } else if (fieldClazz.equals(double.class)) {
            field.setDouble(obj, parcel.readDouble());
        } else if (fieldClazz.equals(long.class)) {
            field.setLong(obj, parcel.readLong());
        } else if (fieldClazz.equals(boolean.class)) {
            field.setBoolean(obj, parcel.readInt() == 1);
        } else {
            field.set(obj, parcel.readValue(obj.getClass().getClassLoader()));
        }
    }
}