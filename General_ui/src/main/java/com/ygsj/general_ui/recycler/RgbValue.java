package com.ygsj.general_ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created by 傅令杰
 * 沉浸式状态栏中使用,存储红绿蓝色值的;
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);//AutoValue_RgbValue是自动生成的类,需要rebuild之后才能正常使用;

    }
}
