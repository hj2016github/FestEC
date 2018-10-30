package com.gehj.general_core.net;

import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 傅令杰 on 2017/4/2
 * 这里不设计具体业务,所以所有的内容方法上的注释都没有具体的路径;
 */
public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);//get有三种方式这里用@QueryMap这种方式;

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);//creat

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);//??

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);//update

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);//??

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Streaming //可以防止内存溢出;
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);//下载,返回值是ResponseBody

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);//上传:MultipartBody是协议
}
