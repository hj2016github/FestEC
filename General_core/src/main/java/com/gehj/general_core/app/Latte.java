package com.gehj.general_core.app;

import android.content.Context;
import android.os.Handler;

import com.gehj.general_core.app.ConfigKeys;
import com.gehj.general_core.app.Configurator;

/**
 * Created by 傅令杰 on 2017/3/29
 */

public final class Latte {//final不希望被修改;
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static Configurator init(Context context) {//在application中调用;
        getConfigurator().getLatteConfigs() //获得到map
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }


    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void test() {
    }
}