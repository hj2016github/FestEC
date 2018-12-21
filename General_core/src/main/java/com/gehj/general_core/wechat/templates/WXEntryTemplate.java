package com.gehj.general_core.wechat.templates;


import com.gehj.general_core.wechat.BaseWXEntryActivity;
import com.gehj.general_core.wechat.LatteWeChat;
/*微信登錄成前的模板activity,代码生成器用这个类生成回调的微信回调的activity
* 这个activity透明(在清单的xml中设定),且立即消失;
* */
public class WXEntryTemplate extends BaseWXEntryActivity {//這個界面是透明的;

    @Override
    protected void onResume() {//可編輯時候直接關閉,且沒有動畫
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {//接口回调;在WXEntryTemplate中的getUserInfo()中进行调用;
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
