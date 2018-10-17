package com.gehj.fastec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;


import com.gehj.general_core.delegates.LatteDelegate;




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
//        testRestClient();
    }

    private void testWX() {
    }
}
