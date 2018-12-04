package com.gehj.general_core.util.timer;

import java.util.TimerTask;

/**
 * Created by 傅令杰 on 2017/4/22
 * 所有公用的部分都提到了core这个项目中;这个类使用接口回调的技术,通过构造传入接口使用;
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener; //构造传入接口
    }

    @Override
    public void run() {
        if (mITimerListener != null) {//接口都需要先判断null;
            mITimerListener.onTimer();
        }
    }
}
