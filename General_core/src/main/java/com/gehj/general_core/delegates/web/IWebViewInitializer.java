package com.gehj.general_core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 傅令杰
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);

    WebViewClient initWebViewClient();//对浏览器的初始化;

    WebChromeClient initWebChromeClient();//对页面内的操作;
}
