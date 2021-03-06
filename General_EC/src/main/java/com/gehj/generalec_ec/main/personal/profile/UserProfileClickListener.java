package com.gehj.generalec_ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.general_core.util.callback.CallbackManager;
import com.gehj.general_core.util.callback.CallbackType;
import com.gehj.general_core.util.callback.IGlobalCallback;
import com.gehj.general_core.util.log.LatteLogger;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.main.personal.list.ListBean;
import com.ygsj.general_ui.date.DateDialogUtil;

/**
 * Created by 傅令杰
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final UserProfileDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};

    public UserProfileClickListener(UserProfileDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(final BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();//id表示头像,年龄,生日等设置...
        switch (id) {
            case 1:
                //开始照相机或选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(Uri args) {
                               /*拍照设置头像*/
                                LatteLogger.d("ON_CROP", args);
                                final ImageView avatar = (ImageView) view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avatar);
                                /*上传头像给服务器时候进行二次请求,更新服务器头像信息*/
                                RestClient.builder()
                                        .url(UploadConfig.UPLOAD_IMG)
                                        .loader(DELEGATE.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                LatteLogger.d("ON_CROP_UPLOAD", response);
                                                final String path = JSON.parseObject(response).getJSONObject("result")
                                                        .getString("path");//取出服务器的头像上传路径;

                                                //通知服务器更新信息
                                                RestClient.builder()
                                                        .url("user_profile.php")//服务器没写,头像上传不能用
                                                        .params("avatar", path)
                                                        .loader(DELEGATE.getContext())
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                //获取更新后的用户信息，然后更新本地数据库(greenDao)
                                                                //没有本地数据的APP，每次打开APP都请求API，获取信息
                                                            }
                                                        })
                                                        .build()
                                                        .post();
                                            }
                                        })
                                        .build()
                                        .upload();
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2://设置姓名,直接跳转姓名设置界面
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3://设置性别
                getGenderDialog(new DialogInterface.OnClickListener() {//1,先点击dialog,选择
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//2,选择后男女后,回调
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4://设置生日
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                /*先定义方法*/
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());//后使用方法,接口回调的方法在showDialog()里面;
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);//初始化
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
