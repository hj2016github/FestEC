package com.gehj.general_core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gehj.general_core.app.Latte;
import com.gehj.general_core.delegates.IPageLoadListener;
import com.gehj.general_core.delegates.web.WebDelegate;
import com.gehj.general_core.delegates.web.route.Router;
import com.gehj.general_core.ui.loader.LatteLoader;
import com.gehj.general_core.util.log.LatteLogger;


/**
 * Created by 傅令杰
 * WebView内部的跳转的处理
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    /*拦截url*/
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);//返回false,webview处理事件;返回true代理处理;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);
    }
}
