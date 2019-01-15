package com.ygsj.general_ui.widget;
//com.ygsj.general_ui.widget.StarLayout
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;
import com.ygsj.general_ui.R;
//import com.ygsj.general_ui.R;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 * 五角星的自定义布局;
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    private static final CharSequence ICON_UN_SELECT = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";
    private static final int STAR_TOTAL_COUNT = 5;//初始的start是五个;
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();
    /*1个参数的构造调用2个参数的构造 2个调用3个*/
    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();//给子view设置星星;
    }

    private void initStarIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());//利用IconTextView进行创建;
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SELECT);//默认的初始状态;
            //star.setTag(R.id.star_count, i);//设置的是第几颗星;R.id.star_count是在ids.xml下放着;
            //star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this);
            STARS.add(star);
            this.addView(star);
        }
    }

    public int getStarCount() {//获取实心星星的个数;
        int count = 0;
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = STARS.get(i);//获取第几颗星;
            final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);//获取点击状态;
          if (isSelect) {
                count++;
            }
        }
        return count;
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            if (i <= count) {//之前与自身的star都变成红色实心的星星;
                final IconTextView star = STARS.get(i);
                star.setText(ICON_SELECTED);
                star.setTextColor(Color.RED);
                star.setTag(R.id.star_is_select, true);
            }
        }
    }

    private void unSelectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i >= count) {//取消好评的星星,之后的变成灰色的空星星;
                final IconTextView star = STARS.get(i);
                star.setText(ICON_UN_SELECT);
                star.setTextColor(Color.GRAY);
                star.setTag(R.id.star_is_select, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final IconTextView star = (IconTextView) v;
        //获取第几个星星
        final int count = (int) star.getTag(R.id.star_count);
        //获取点击状态
        final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
        if (!isSelect) {
            selectStar(count);
        } else {
            unSelectStar(count);
        }
    }
}
