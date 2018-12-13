package com.gehj.fastec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.IError;
import com.gehj.general_core.net.callback.IFailure;
import com.gehj.general_core.net.callback.IRequest;
import com.gehj.general_core.net.callback.ISuccess;

import static android.content.ContentValues.TAG;


/**
 * Created by 傅令杰 on 2017/4/2
 */

public class ExampleDelegate extends LatteDelegate {//使用的fragment;
    /*调用的时候直接调用实现类的方法*/
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
        RestClient.builder().url("http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=fec/singup2")//test是Application中第一个参数,要进行拦截的地址;
                .loader(getContext())//走的是默认的加载圈,我的加载时通过visible与invisible视图进行控制,这里是灵活的弹出;
                .params("","")
                .success(new ISuccess() {//相当于handler在UI线程中的代码;
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
                    public void onError(final int code, final String msg) {
                        Toast.makeText(_mActivity, code+msg, Toast.LENGTH_SHORT).show();

                    }
                }).build().get();
    }
}
