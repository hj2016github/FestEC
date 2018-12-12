package com.gehj.generalec_ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gehj.general_core.app.AccountManager;
import com.gehj.generalec_ec.database.DatabaseManager;
import com.gehj.generalec_ec.database.UserProfile;


/**
 * Created by 傅令杰 on 2017/4/22
 * 这个类作用解析是json-调用数据库,插入解析后的数据,供SingXXDelegate.java登录成功后调用;
 */

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");//json串的key拿到的实体;用fastjson进行解析;
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(profile);//插入数据库;

        //已经登录成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }


    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(0, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册成功了
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
}
