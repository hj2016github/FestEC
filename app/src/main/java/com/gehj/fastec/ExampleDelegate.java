package com.gehj.fastec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.IError;
import com.gehj.general_core.net.callback.IFailure;
import com.gehj.general_core.net.callback.ISuccess;


/**
 * Created by 傅令杰 on 2017/4/2
 */

public class ExampleDelegate extends LatteDelegate {//使用的fragment;

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

   /* @OnClick(R.id.btn_test)
    void onClickTest() {
        testWX();

    }*/

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        testRestClient();
    }

    private void testWX() {
    }

    private  void testRestClient(){
        RestClient.builder().url("https://www.baidu.com")
                .params("","")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(_mActivity, response, Toast.LENGTH_SHORT).show();
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
                }).build().get();
    }
}
