package com.lidiwo.parcelable.constant;


public class Consts {
    public static final String PROJECT = "Parcelable";
    public static final String SEPARATOR = "$$";


    //需要处理的注解类的全路径
    public static final String ANN_TYPE_PARCELABLE = "com.lidiwo.parcelable.annotation.Parcelable";

    // 生成的类名后缀
    public static final String NAME_OF_SUFFIX = SEPARATOR+PROJECT;

    //生成的类的包名
    public static final String PACKAGE_OF_GENERATE_FILE = "com.lidiwo.parcelable.bean";

    // Log
    public static final String PREFIX_OF_LOGGER = PROJECT + "::Compiler ";

}
