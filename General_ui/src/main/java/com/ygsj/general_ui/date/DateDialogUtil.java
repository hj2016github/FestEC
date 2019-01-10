package com.ygsj.general_ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by 傅令杰
 * 选择生日的对话框接口
 */

public class DateDialogUtil {

    public interface IDateListener {

        void onDateChange(String date);
    }

    private IDateListener mDateListener = null;

    public void setDateListener(IDateListener listener) {
        this.mDateListener = listener;
    }
    /*以上回调接口要使用*/


    public void showDialog(Context context) {
        final LinearLayout ll = new LinearLayout(context);
        final DatePicker picker = new DatePicker(context);//设置日历选择;
        final LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        picker.setLayoutParams(lp);

        picker.init(1990, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {//初始化日历选择;
                /*以下为了选择timepiker后,利用Calendar把值传给接口,从而回传给跳转前页面的textview*/
                final Calendar calendar = Calendar.getInstance();//java包下的
                calendar.set(year, monthOfYear, dayOfMonth);
                final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
                final String data = format.format(calendar.getTime());
                if(mDateListener!=null){
                    mDateListener.onDateChange(data);//接口传值,最终返回给点击前的txetview;
                }
            }
        });

        ll.addView(picker);

        new AlertDialog.Builder(context)
                .setTitle("选择日期")
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
