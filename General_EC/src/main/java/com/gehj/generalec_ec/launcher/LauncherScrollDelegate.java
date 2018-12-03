package com.gehj.generalec_ec.launcher;

import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.util.storage.LattePreference;
import com.gehj.generalec_ec.R;
import com.ygsj.general_ui.LauncherHolderCreator;


import java.util.ArrayList;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class LauncherScrollDelegate extends LatteDelegate  implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;//需要传递图片;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();//图片的list
   // private ILauncherListener mILauncherListener = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//框架中代码代替图片
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//水平居中
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }*/
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
        /*if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否已经登录
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
    }*/
    }
}
