package com.gehj.general_core.net.callback;

/**
 * Created by 傅令杰 on 2017/4/2
 */

public interface IRequest {

    void onRequestStart();//请求开始的加载圈;

    void onRequestEnd();//请求结束加载圈消失;
}
