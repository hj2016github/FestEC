package com.gehj.general_core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.gehj.general_core.Latte;

/**
 * Created by 傅令杰 on 2017/4/2
 */

public final class DimenUtil {
    static final  Resources resources = Latte.getApplicationContext().getResources();
    public static int getScreenWidth() {//得到屏幕的宽;
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {//得到屏幕的高;
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
