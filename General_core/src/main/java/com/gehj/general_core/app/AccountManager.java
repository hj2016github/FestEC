package com.gehj.general_core.app;


import com.gehj.general_core.util.storage.LattePreference;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {//登录注册成功后要调用一次并设置为true;
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {// 是否已经登录
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();//接口回调;
        } else {
            checker.onNotSignIn();
        }
    }
}
