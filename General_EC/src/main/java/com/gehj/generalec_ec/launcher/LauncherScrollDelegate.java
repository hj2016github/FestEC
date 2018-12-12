package com.gehj.generalec_ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gehj.general_core.app.AccountManager;
import com.gehj.general_core.app.IUserChecker;
import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.util.storage.LattePreference;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.sign.SignInDelegate;
import com.ygsj.general_ui.LauncherHolderCreator;
import com.ygsj.general_ui.launcher.ILauncherListener;
import com.ygsj.general_ui.launcher.OnLauncherFinishTag;
import com.ygsj.general_ui.launcher.ScrollLauncherTag;


import java.util.ArrayList;

/**
 * Created by 傅令杰 on 2017/4/22
 * 这个fragment完全是借用第三方写的,没有布局
 */

public class LauncherScrollDelegate extends LatteDelegate  implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;//ConvenientBanner线性布局,需要传递图片;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();//图片的list
    private ILauncherListener mILauncherListener = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner//线性布局
                .setPages(new LauncherHolderCreator(), INTEGERS)//传入图片
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//框架中代码代替图片,时指示的小圆点;
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//水平居中
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {//初始化view;
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {//调用行为
        initBanner();
    }


    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);//说明是第一进入,以后不用进入;
            getSupportDelegate().start(new SignInDelegate(), SINGLETASK);//强制进入登录界面;
            //TODO 如果是展示类app,应该直接进入主界面,某些功能进行登录;如果是个人型app,强制跳转注册界面或者登录界面;
        }
    }

}
