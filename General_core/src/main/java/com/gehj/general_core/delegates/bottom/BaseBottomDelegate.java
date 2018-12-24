package com.gehj.general_core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.gehj.general_core.R;
import com.gehj.general_core.R2;
import com.gehj.general_core.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by 傅令杰
 * 底层的tab
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();//存储子bean;
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();//存储所有的子fragment;
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;//记录当前的fragment是哪个fragment;
    private int mIndexDelegate = 0;//第一个展示的fragment
    private int mClickedColor = Color.RED;//点击后变色;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);//使用时强制赋值;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    public abstract int setIndexDelegate();//指定第一个展示页面fragment;

    @ColorInt //必须是颜色;
    public abstract int setClickedColor();//点击后变色;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);//外界传入的赋给本地;
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);//循环添加给子bean;
            ITEM_DELEGATES.add(value);//循环添加给子delegate;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final int size = ITEMS.size();//不要在循环中取size否则效率太低;
        for (int i = 0; i < size; i++) {
            /*底部的横向liner包含四个小tab*/
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);//把relativelayout放入linerlayout中;
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);//item为每一个的小tab;
            //设置每个item的点击事件
            item.setTag(i);//之后还要用i;
            item.setOnClickListener(this);
            //分解item(包含IconText(图片(上)与文字(下)))
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());//外界设置的填入;
            itemTitle.setText(bean.getTitle());//外界设置的填入;
            if (i == mIndexDelegate) {//循环的角标正好是刚进页面需要点击的tab;
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }

        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);//加载多个同级根Fragment,类似Wechat, QQ主页的场景
    }

    private void resetColor() {//其他的fragment变成不可点击状态;
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;//回调监听的view;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));//show一个隐藏一个,先show,后隐藏;
        //注意先后顺序
        mCurrentDelegate = tag;//点击当前页(tag)赋值当前页;
    }
}
