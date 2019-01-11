package com.gehj.general_core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.util.callback.CallbackManager;
import com.gehj.general_core.util.callback.CallbackType;
import com.gehj.general_core.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by 傅令杰
 */

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null) {
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
