package com.gehj.general_core.net.callback;

import android.os.Handler;


import com.gehj.general_core.app.ConfigKeys;
import com.gehj.general_core.app.Latte;
import com.gehj.general_core.net.RestCreator;
import com.gehj.general_core.ui.loader.LatteLoader;
import com.gehj.general_core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 傅令杰 on 2017/4/2
 * 重写了callback,其实直接调用的就是callback的response,只不过多加了一层;
 */

public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = Latte.getHandler();//Handler静态类型可以避免内存泄漏;

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());//接口传值;
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());//接口传值;
            }
        }

         onRequestFinish();//response回调结束结束loader;
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        onRequestFinish();//response回调结束结束loader;
    }

    private void onRequestFinish() {//延时加载loader;
        final long delayed = Latte.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    LatteLoader.stopLoading();
                }
            }, delayed);
        }
    }
}
