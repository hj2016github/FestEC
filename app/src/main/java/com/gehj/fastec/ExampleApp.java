package com.gehj.fastec;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gehj.fastec.event.ShareEvent;
import com.gehj.fastec.event.TestEvent;
import com.gehj.general_core.app.Latte;
import com.gehj.general_core.net.interceptors.DebugInterceptor;
import com.gehj.general_core.util.callback.CallbackManager;
import com.gehj.general_core.util.callback.CallbackType;
import com.gehj.general_core.util.callback.IGlobalCallback;
import com.gehj.generalec_ec.database.DatabaseManager;
import com.gehj.generalec_ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;


import com.gehj.general_core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 傅令杰 on 2017/3/29
 */
public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局变量;
        Latte.init(this)
                /*https://fontawesome.com/?from=io*/
                /*https://www.cnblogs.com/zyw-205520/p/7266225.html?utm_source=debugrun&utm_medium=referral*/
                //TODO 可以用到大宁等项目中;
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())//自定义字体,从淘宝的icon中直接可以下载;
                .withLoaderDelayed(1000)
                .withApiHost("http://127.0.0.1")
                //.withInterceptor(new DebugInterceptor("test", R.raw.test)) //delegate的地址合起来是http://127.0.0.1/test/
                .withWeChatAppId("你的微信AppKey")//本项目在微信登录无法回调;
                .withWeChatAppSecret("你的微信AppSecret")//本项目在微信登录无法回调;
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share", new ShareEvent())//一键分享
                .configure();
       // initStetho();
        DatabaseManager.getInstance().init(this);//初始化greenDao;

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        /*解耦:在onCreat中进行初始化接口,之后再core的setting界面进行回调*/
        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //极光推送结束,开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        //极光推送没有结束,结束极光推送
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
