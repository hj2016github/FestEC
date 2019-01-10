package com.gehj.generalec_ec.main.personal.list;

import android.support.v7.widget.SwitchCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gehj.generalec_ec.R;

import java.util.List;

/**
 * Created by 傅令杰
 * 各种不同类的recycleview的设置;
 * 1,我的界面下的条目 2,我的头像 3,切换
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();
    /*设置类型方便区分,在一个recycleview上可以操纵不同xml*/
    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);//personal界面;
        addItemType(ListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar);//我的头像界面;
        addItemType(ListItemType.ITEM_SWITCH,R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_AVATAR://头像的更换
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_arrow_avatar));
                break;
            case ListItemType.ITEM_SWITCH:
                helper.setText(R.id.tv_arrow_switch_text,item.getText());
                final SwitchCompat switchCompat = helper.getView(R.id.list_item_switch);
                switchCompat.setChecked(true);
                switchCompat.setOnCheckedChangeListener(item.getmOnCheckedChangeListener());
                break;
            default:
                break;
        }
    }
}
