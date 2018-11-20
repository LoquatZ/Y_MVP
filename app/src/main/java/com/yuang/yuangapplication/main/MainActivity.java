package com.yuang.yuangapplication.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuang.library.base.BaseActivity;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.entity.ActivityBean;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BaseQuickAdapter<ActivityBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar, "Y_Library", false);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<ActivityBean, BaseViewHolder>(R.layout.item_recycler_view_main) {
            @Override
            protected void convert(BaseViewHolder helper, ActivityBean item) {
                helper.setText(R.id.item, item.getActivityName());
                helper.addOnClickListener(R.id.item);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ActivityBean activityBean = (ActivityBean) adapter.getData().get(position);
            startActivity(activityBean.getActivityClass());
        });
    }

    @Override
    protected void initData() {
        mPresenter.getActivity();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setActivity(List<ActivityBean> activity) {
        mAdapter.setNewData(activity);
    }

}
