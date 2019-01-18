package com.gehj.general_core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by 傅令杰
 */

public class ScanView extends ZBarScannerView {

    public ScanView(Context context) {
        this(context, null);//一个调两个参数的;
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    /*打开二维码扫描框*/
    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new LatteViewFinderView(context);
    }
}
