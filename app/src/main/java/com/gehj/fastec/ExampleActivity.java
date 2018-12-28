package com.gehj.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.gehj.general_core.activities.ProxyActivity;
import com.gehj.general_core.app.Latte;
import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.generalec_ec.launcher.LauncherDelegate;
import com.gehj.generalec_ec.launcher.LauncherScrollDelegate;
import com.gehj.generalec_ec.main.EcBottomDelegate;
import com.gehj.generalec_ec.sign.ISignListener;
import com.gehj.generalec_ec.sign.SignInDelegate;
import com.gehj.generalec_ec.sign.SignUpDelegate;
import com.ygsj.general_ui.launcher.ILauncherListener;
import com.ygsj.general_ui.launcher.OnLauncherFinishTag;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;


public class ExampleActivity extends ProxyActivity implements ISignListener
         ,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*以下代码隐藏actionBar*/
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);//給微信準備全局的activity;
        StatusBarCompat.translucentStatusBar(this, true);//沉浸式状态栏

    }
    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {//登录的回调;
        switch (tag) {
            case SIGNED:
               Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                //getSupportDelegate().startWithPop(new EcBottomDelegate());
                //getSupportDelegate().start(new EcBottomDelegate());//进入主页
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().start(new EcBottomDelegate());//进入主页

                //getSupportDelegate().start(new SignInDelegate());//重新登录
               // getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            default:
                break;
        }
    }
}
