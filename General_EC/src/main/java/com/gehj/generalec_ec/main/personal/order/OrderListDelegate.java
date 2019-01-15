package com.gehj.generalec_ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.R2;
import com.gehj.generalec_ec.main.personal.PersonalDelegate;
import com.ygsj.general_ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 傅令杰
 * 订单列表;
 */

public class OrderListDelegate extends LatteDelegate {

    private String mType = null;

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        /*mType是从personal页面传过来的*/
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=/fec/orderlist/")
                .params("type", mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new OrderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));//点击跳转评论页面;
                    }
                })
                .build()
                .get();
    }
}
