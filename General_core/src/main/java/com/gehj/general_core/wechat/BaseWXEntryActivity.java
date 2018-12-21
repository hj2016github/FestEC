package com.gehj.general_core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.IError;
import com.gehj.general_core.net.callback.IFailure;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.general_core.util.log.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by 傅令杰 on 2017/4/25
 * 微信登錄回调頁面;
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求到第三方应用后的回调
    @Override
    public void onReq(BaseReq baseReq) {
    }

    //第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl //請求Accesstoken與openID的地址
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        LatteLogger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());//發送請求Accesstoken與openID
    }

    private void getAuth(String authUrl) {//第二次請求;
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());//請求user的信息名字/頭像等;

                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);//接口迴調:迴調抽象方法;
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
