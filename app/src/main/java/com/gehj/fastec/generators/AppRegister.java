package com.gehj.fastec.generators;


import com.gehj.general_annotation.AppRegisterGenerator;
import com.gehj.general_core.wechat.templates.AppRegisterTemplate;

/**
 * Created by 傅令杰 on 2017/4/22
 */
@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.gehj.fastec",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
