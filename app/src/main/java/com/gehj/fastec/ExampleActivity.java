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
import com.gehj.generalec_ec.sign.ISignListener;
import com.gehj.generalec_ec.sign.SignInDelegate;
import com.gehj.generalec_ec.sign.SignUpDelegate;
import com.ygsj.general_ui.launcher.ILauncherListener;
import com.ygsj.general_ui.launcher.OnLauncherFinishTag;


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
        Latte.getConfigurator().withActivity(this);
        //StatusBarCompat.translucentStatusBar(this, true);

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

   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
     //   StatusBarCompat.translucentStatusBar(this, true);

    }*/

    /*@Override
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
    public LatteDelegate setRootDelegate() {
        return new EcBottomDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }*/

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {//登录的回调;
        switch (tag) {
            case SIGNED:
               Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                //getSupportDelegate().startWithPop(new EcBottomDelegate());
                getSupportDelegate().start(new ExampleDelegate());//进入主页
                break;
            case NOT_SIGNED:
               Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().start(new SignInDelegate());//重新登录
                break;
            default:
                break;
        }
    }
}
