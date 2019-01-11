package com.gehj.general_core.net;

import android.content.Context;

import com.gehj.general_core.net.callback.IError;
import com.gehj.general_core.net.callback.IFailure;
import com.gehj.general_core.net.callback.IRequest;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.general_core.net.callback.RequestCallbacks;
import com.gehj.general_core.net.download.DownloadHandler;
import com.gehj.general_core.ui.loader.LatteLoader;
import com.gehj.general_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;//加载圈;
    private final File FILE;
    private final Context CONTEXT;

    RestClient(String url,
               Map<String, Object> params,String downloadDir,
               String extension, String name,
               IRequest request, ISuccess success,
               IFailure failure,  IError error,
               RequestBody body,  File file,
               Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();//1,静态内部内创建 2,Retrofit进行对接口代理;
        Call<String> call = null;

        if (REQUEST != null) {//这个方法没有调用,可以在前台activity中进行调用;
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {//加载圈;
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {//method是枚举
            case GET:
                call = service.get(URL, PARAMS);//接口的注解方法;返回retrofit的call;
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback()); //类似于okhttp的 OkhttpClient.newCall(request).enqueue(callback);
        }
    }

    private Callback<String> getRequestCallback() {//接口回调的传给call处理;
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }
    //以下调用requst(Method);
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {//post_raw需要requestBody;
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {//用BODY时PARAMS需要时空的;
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();//作者是用异步任务(Asyntask)进行下载的;
    }
}
