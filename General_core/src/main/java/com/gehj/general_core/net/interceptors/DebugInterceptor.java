package com.gehj.general_core.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;


import com.gehj.general_core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 傅令杰 on 2017/4/11
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;//模拟url;
    private final int DEBUG_RAW_ID;//资源id,在app的资源文件夹

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {//参数2是要传入的参数;
        return new Response.Builder()
                .code(200) //假设成功;
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)//协议用http1.1
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {//@RawRes 安卓的元注解,避免出错
        final String json = FileUtil.getRawFile(rawId);//从文件中读取json;
        return getResponse(chain, json);//调用getResponse;
    }

    /*实现intercepet的方法,baseIntercepetor是抽象类,可以不实现;*/
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);//如果有debug关键字,返回时用自己写的response;
        }
        return chain.proceed(chain.request());//否则原样返回;
    }
}
