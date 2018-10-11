```
    一个用于帮助 Android App 进行对象快速Parcelable框架,给需要序列化的Bean对象添加@Parcelable 注解，即可完成复杂、麻烦的Android原生序列化Parcelable
```
#### 最新版本

模块|parcelable-core|parcelable-compiler|parcelable-annotation
---|---|---|---
最新版本|[ ![Download](https://api.bintray.com/packages/lidiwo/Parcelable/parcelable-core/images/download.svg) ](https://bintray.com/lidiwo/Parcelable/parcelable-core/_latestVersion)|[ ![Download](https://api.bintray.com/packages/lidiwo/Parcelable/parcelable-compiler/images/download.svg) ](https://bintray.com/lidiwo/Parcelable/parcelable-compiler/_latestVersion)|[ ![Download](https://api.bintray.com/packages/lidiwo/Parcelable/parcelable-annotation/images/download.svg) ](https://bintray.com/lidiwo/Parcelable/parcelable-annotation/_latestVersion)

#### 一、支持的对象字段类型

1. **基本数据类型：int、long、float、double、boolean、byte**
2. **引用数据类型：String、Object**
3. **基本数据类型包装类：Integer、Long、Float、Double、Boolean、Byte、Short**
4. **数组：int[]、long[]、double[]、boolean[]、byte[]、String[]、CharSequence[]**
5. **集合：List<T>、 Map<K,V>**
6. **其他：CharSequence、SparseArray<T>**

#### 二、用法

1. 添加依赖和配置
``` gradle
dependencies {
    // 替换成最新版本, 需要注意的是api
    // 要与compiler匹配使用，均使用最新版可以保证兼容

    implementation 'com.lidiwo:parcelable-core:x.x.x'
    annotationProcessor 'com.lidiwo:parcelable-compiler:x.x.x'
    ...
}
// 旧版本gradle插件(< 2.2)，可以使用apt插件，配置方法请自行查询'
```

2. 添加注解
``` java
// 在需要序列化的对象上面添加注解上添加注解(必选)
@Parcelable
public class StudentBean {
    ...
}
```

3. 使用
``` java
// 传递序列化对象(传递对象必须添加@Parcelable注解)
StudentBean bean=new StudentBean();

Intent intent=new Intent(this,XxxxActivity.class);
intent.putExtra("Student", ParcelableManager.createParcelable(bean));
startActivity(intent);

//获取序列化对象
StudentBean bean = getIntent().getParcelableExtra("Student");
```

4. 添加混淆规则(如果使用了Proguard)
```
-keep class * implements android.os.Parcelable
```

#### 三、使用注意事项

1. **被@Parcelable注解的对象必须要有无参构造函数**
2. **被@Parcelable注解的对象有父类的时候，父类可以不使用@Parcelable注解**
3. **被@Parcelable注解的对象字段中引入了其他引用对象的时候，改对象必须使用@Parcelable注解**

#### 四、问题反馈

 如果发现有使用问题，可以给我发邮件kolan9527@126.com






