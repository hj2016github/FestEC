package com.gehj.generalec_ec.launcher;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.gehj.general_core.app.AccountManager;
import com.gehj.general_core.app.IUserChecker;
import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.util.storage.LattePreference;
import com.gehj.general_core.util.timer.BaseTimerTask;
import com.gehj.general_core.util.timer.ITimerListener;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.R2;
import com.ygsj.general_ui.launcher.ILauncherListener;
import com.ygsj.general_ui.launcher.OnLauncherFinishTag;
import com.ygsj.general_ui.launcher.ScrollLauncherTag;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 傅令杰 on 2017/4/22
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {


    @BindView(R2.id.tv_launcher_timer) //绑定倒计时;
    AppCompatTextView mTvTimer = null;

    private Timer mTimer = null;
    private int mCount = 5;//倒计时从5秒开始;
    private ILauncherListener mILauncherListener = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {//提前结束倒计时
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();//直接显示主页;
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);//直接回调这个类的onTimer方法;
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {//view
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {//行为
        initTimer();
    }

    //根据是否是第一次登陆,判断是否显示滑动启动页
    private void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {//是否是第一次登陆
            getSupportDelegate().start(new LauncherScrollDelegate(), SINGLETASK);//不是第一次登陆显示轮播广告;
        } else {
            //检查用户是否登录了APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    public void onTimer() {//timer结束时候的回调,1秒钟调用一次这个方法;
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
