package com.lidiwo.parcelable.compiler.processor;

import android.os.Parcel;

import com.google.auto.service.AutoService;
import com.lidiwo.parcelable.annotation.Parcelable;
import com.lidiwo.parcelable.compiler.utils.Logger;
import com.lidiwo.parcelable.compiler.utils.Utils;
import com.lidiwo.parcelable.constant.Consts;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 11:50
 * @Company：智能程序员
 * @Description： *****************************************************
 */

/**
 * 自动注册
 */
@AutoService(Processor.class)
/**
 * 指定使用的Java版本 替代 {@link AbstractProcessor#getSupportedSourceVersion()} 函数
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
/**
 * 注册给哪些注解的  替代 {@link AbstractProcessor#getSupportedAnnotationTypes()} 函数
 */
@SupportedAnnotationTypes({Consts.ANN_TYPE_PARCELABLE})
public class ParcelableProcessor extends AbstractProcessor {
    /**
     * 文件生成器 类/资源
     */
    private Filer filerUtils;

    private Logger log;

    /**
     * 初始化 从 {@link ProcessingEnvironment} 中获得一系列处理器工具
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //获得apt的日志输出
        log = new Logger(processingEnvironment.getMessager());
        filerUtils = processingEnvironment.getFiler();
    }

    /**
     * 相当于main函数，正式处理注解
     *
     * @param annotations 使用了支持处理注解  的节点集合
     * @param roundEnv    表示当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理(已经处理)
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!Utils.isEmpty(annotations)) {
            //获取被Parcelable注解的节点集合
            Set<? extends Element> parcelableElements = roundEnv.getElementsAnnotatedWith(Parcelable.class);
            if (!Utils.isEmpty(parcelableElements)) {
                processParcelable(parcelableElements);
            }
            return true;
        }
        return false;
    }

    /**
     * 处理被被注解的节点
     *
     * @param parcelableElements
     */
    private void processParcelable(Set<? extends Element> parcelableElements) {
        for (Element parcelableElement : parcelableElements) {
            try {
                generatedParcelable(parcelableElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generatedParcelable(Element parcelableElement) throws Exception {
        //创建构造方法1
        MethodSpec constructorMethod1 = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Parcel.class, "parcel")
                .addCode("com.lidiwo.parcelable.core.utils.ParcelableUtils.readFromParcel(parcel,this);\n")
                .build();

        //创建构造方法2
        MethodSpec constructorMethod2 = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(parcelableElement.asType()), "parcelableBean")
                .addCode("com.lidiwo.parcelable.core.utils.ParcelableUtils.cloneBean(parcelableBean,this);\n")
                .build();

        //创建describeContents方法
        MethodSpec method1 = MethodSpec.methodBuilder("describeContents")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("return 0")
                .addAnnotation(Override.class)
                .build();

        //创建writeToParcel方法
        MethodSpec method2 = MethodSpec.methodBuilder("writeToParcel")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(Parcel.class, "dest")
                .addParameter(int.class, "flags")
                .addAnnotation(Override.class)
                .addCode("com.lidiwo.parcelable.core.utils.ParcelableUtils.writeToParcel(dest,flags,this);\n")
                .build();

        //定义生成类名
        String className = parcelableElement.getSimpleName().toString().concat(Consts.NAME_OF_SUFFIX);

        ClassName creatorClass = ClassName.get("android.os", "Parcelable.Creator");
        ClassName parcelableClass = ClassName.get(Consts.PACKAGE_OF_GENERATE_FILE, className);

        //生成匿名函数
        TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(creatorClass, parcelableClass))
                .addMethod(MethodSpec.methodBuilder("createFromParcel")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Parcel.class, "source")
                        .returns(parcelableClass)
                        .addStatement("return new $L(source)", className)
                        .build())
                .addMethod(MethodSpec.methodBuilder("newArray")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(int.class, "size")
                        .returns(ArrayTypeName.of(parcelableClass))
                        .addStatement("return new $L[size]", className)
                        .build())
                .build();

        //匿名函数字段
        FieldSpec android = FieldSpec.builder(ParameterizedTypeName.get(creatorClass, parcelableClass), "CREATOR")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L", comparator)
                .build();

        //创建类
        TypeSpec helloWorld = TypeSpec.classBuilder(className)
                .superclass(ClassName.get(parcelableElement.asType()))
                .addSuperinterface(ClassName.get(android.os.Parcelable.class))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructorMethod1)
                .addMethod(constructorMethod2)
                .addMethod(method1)
                .addMethod(method2)
                .addField(android)
                .build();

        //创建java文件
        JavaFile javaFile = JavaFile.builder(Consts.PACKAGE_OF_GENERATE_FILE, helloWorld)
                .build();
        javaFile.writeTo(filerUtils);
    }
}
