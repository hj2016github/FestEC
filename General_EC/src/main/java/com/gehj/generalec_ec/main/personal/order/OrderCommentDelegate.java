package com.gehj.generalec_ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.util.callback.CallbackManager;
import com.gehj.general_core.util.callback.CallbackType;
import com.gehj.general_core.util.callback.IGlobalCallback;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.R2;
import com.ygsj.general_ui.widget.AutoPhotoLayout;
import com.ygsj.general_ui.widget.StarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 傅令杰
 * 商品评论界面,五角星以及上传图片控件;
 */

public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit() {
        Toast.makeText(getContext(), "评分： " + mStarLayout.getStarCount(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {//ON_CROP裁剪上传的标志;
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });
    }
}
