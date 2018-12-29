package com.gehj.general_core.delegates;

/**
 * Created by 傅令杰 on 2017/4/2
 *这个fragment是正式使用的delegate;
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {
    /*获取父fragment,在indexDelegate中的点击商品处使用*/
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
