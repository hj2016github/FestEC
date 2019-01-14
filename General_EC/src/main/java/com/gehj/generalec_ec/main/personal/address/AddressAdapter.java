package com.gehj.generalec_ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.generalec_ec.R;
import com.ygsj.general_ui.recycler.MultipleFields;
import com.ygsj.general_ui.recycler.MultipleItemEntity;
import com.ygsj.general_ui.recycler.MultipleRecyclerAdapter;
import com.ygsj.general_ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by 傅令杰
 */

public class AddressAdapter extends MultipleRecyclerAdapter {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = entity.getField(MultipleFields.NAME);
                final String phone = entity.getField(AddressItemFields.PHONE);
                final String address = entity.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = entity.getField(MultipleFields.TAG);
                final int id = entity.getField(MultipleFields.ID);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = holder.getView(R.id.tv_address_delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                .url("http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=/fec/address")//没有写后台服务,删除后并不同步;
                                .params("id", id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(holder.getLayoutPosition());
                                    }
                                })
                                .build()
                                .get();
                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
            default:
                break;
        }
    }
}
