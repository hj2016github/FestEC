package com.gehj.generalec_ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.gehj.general_core.delegates.bottom.BottomItemDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.R2;
import com.gehj.generalec_ec.main.EcBottomDelegate;
import com.joanzapata.iconify.widget.IconTextView;
import com.ygsj.general_ui.recycler.BaseDecoration;
import com.ygsj.general_ui.refresh.RefreshHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 傅令杰
 */

public class IndexDelegate extends BottomItemDelegate {
        //implements View.OnFocusChangeListener {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;

   // @OnClick(R2.id.icon_index_scan)
   // void onClickScanQrCode() {
    //    startScanWithCheck(this.getParentDelegate());
   // }

//TODO 使用handler的思想
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());

        //测试:
    /*    RestClient.builder().url("index.php").success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                    final IndexDataConverter converter = new IndexDataConverter();
                    converter.setJsonData(response);
                    final ArrayList<MultipleItemEntity> list = converter.convert();
                    final String image = list.get(1).getField(MultipleFields.IMAGE_URL);
                Toast.makeText(_mActivity, image, Toast.LENGTH_SHORT).show();
            }
        }).build().get();*/
       /* CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();
                    }
                });
        mSearchView.setOnFocusChangeListener(this);*/
    }
    /*下拉刷新,在懒加载中进行调用*/
    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(//三个圈的变化;
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);//参数:球是否由小变大,起始高度,终止高度;
    }
    /*recycleview始化布局*/
    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        /*每个商品的点击事件,连同BottomBar也进行相应的跳转*/
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }


    //TODO 安卓中的懒加载;
    /*fragmention的懒加载*/
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        //http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=fec/index
        mRefreshHandler.firstPage("http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=fec/index");//首页数据的请求,作者本地架起来php服务器;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

   /* @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentDelegate().getSupportDelegate().start(new SearchDelegate());
        }
    }*/
}
