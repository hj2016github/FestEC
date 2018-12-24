package com.gehj.general_core.delegates.bottom;

import android.view.View;
import android.widget.Toast;

import com.gehj.general_core.R;
import com.gehj.general_core.app.Latte;
import com.gehj.general_core.delegates.LatteDelegate;


/**
 * Created by 傅令杰
 * 每一个tab页面的基类
 */

public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener{
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    /*点击两次退出*/
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();//_mActivity为宿主activity;
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;//事件已经消费掉,不需要系统进行额外的处理;
    }

    /*focus重新request*/
    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }
}
