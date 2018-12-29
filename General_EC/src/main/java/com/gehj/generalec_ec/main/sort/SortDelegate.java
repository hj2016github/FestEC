package com.gehj.generalec_ec.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.gehj.general_core.delegates.bottom.BottomItemDelegate;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.main.sort.content.ContentDelegate;
import com.gehj.generalec_ec.main.sort.list.VerticalListDelegate;


/**
 * Created by 傅令杰
 */

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    /*绑定子布局放在懒加载中,在点击分类时候才渲染,如果在onBindView中则在加载整个布局时就进行渲染*/
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
       super.onLazyInitView(savedInstanceState);
        /*左侧的分类*/
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        //设置右侧第一个分类显示，默认显示分类1
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }
}
