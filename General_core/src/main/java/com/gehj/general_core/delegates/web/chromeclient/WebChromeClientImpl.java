package com.gehj.general_core.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by 傅令杰
 */

public class WebChromeClientImpl extends WebChromeClient {
    /*拦截对话框,弹出自己对话框*/
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
