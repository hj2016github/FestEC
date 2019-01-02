package com.gehj.generalec_ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


import com.gehj.general_core.delegates.LatteDelegate;
import com.gehj.general_core.net.RestClient;
import com.gehj.general_core.net.callback.ISuccess;
import com.gehj.generalec_ec.R;
import com.gehj.generalec_ec.R2;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 傅令杰
 * 分类中右侧的内容,点击列表后切换
 */

public class ContentDelegate extends LatteDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private List<SectionBean> mData = null;

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;
    /*onCreat时先获取id进行展示*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }
    /*创建实例的时候保存id号*/
    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData() {
        RestClient.builder()
                .url("http://mock.eolinker.com/Vw4Pz6ib2c6ac93793e296a2d8acbb4e6ed0b424abea5ae?uri=fec/content?contentId=" + mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter =
                                new SectionAdapter(R.layout.item_section_content,
                                        R.layout.item_section_header, mData);//参数:内容,头,数据
                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//两列
        mRecyclerView.setLayoutManager(manager);
        initData();
    }
}
