package com.ygsj.general_ui;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by 傅令杰 on 2017/4/22
 * 导航使用的是第三的轮播插件需要写自定义的holder
 * 导航页直接传入的是图片
 */

public class LauncherHolder implements Holder<Integer> {//recycleview的holder

    private AppCompatImageView mImageView = null;


    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
