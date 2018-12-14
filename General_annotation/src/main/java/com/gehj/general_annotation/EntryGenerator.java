package com.gehj.general_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 傅令杰 on 2017/4/22 用来传入包名与微信的模板代码
 */
@Target(ElementType.TYPE) // 类注解
@Retention(RetentionPolicy.SOURCE) // 编译时处理
public @interface EntryGenerator {
    String packageName();

    Class<?> entryTemplate();// 实体类模板
}
