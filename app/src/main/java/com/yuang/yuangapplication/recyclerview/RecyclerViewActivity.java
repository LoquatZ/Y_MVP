package com.yuang.yuangapplication.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yuang.library.base.BaseActivity;
import com.yuang.library.utils.AdapterUtils;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.entity.GankItemBean;
import com.yuang.yuangapplication.utils.GlideApp;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称: RecyclerViewActivity
 * 类描述: RecyclerViewDemo
 * 创建人: Yuang
 * 创建时间: 2018/11/20 5:05 PM
 */
public class RecyclerViewActivity extends BaseActivity<RecyclerViewPresenter, RecyclerViewModel>
        implements RecyclerViewContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private BaseQuickAdapter<GankItemBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclear_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar("RecyclerViewActivity")
                .setBackground(R.drawable.bg_toolbar)
                .setLeftImage(R.mipmap.ic_back);

        refreshLayout.setRefreshing(true);
        mAdapter = new BaseQuickAdapter<GankItemBean, BaseViewHolder>(R.layout.item_tv_other) {
            @Override
            protected void convert(BaseViewHolder helper, GankItemBean item) {
                GlideApp.with(getContext())
                        .load(item.getUrl())
                        .thumbnail(0.4f)//缩略图尺寸
//                        .skipMemoryCache(true)//跳过缓存到内存
                        .transition(new DrawableTransitionOptions().crossFade())//缩放动画
                        .into((ImageView) helper.getView(R.id.thumnails));
            }
        };
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnLoadMoreListener(() -> mPresenter.getNextPage(), recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
//        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(() -> mPresenter.getFirstPage());
    }

    @Override
    protected void initData() {
        mPresenter.getFirstPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    /**
     * RecyclerView 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(RecyclerViewActivity.this)
                    .setBackground(R.drawable.selector_red)
                    .setText("删除")
                    .setTextColor(getResources().getColor(R.color.white))
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
        menuBridge.closeMenu();
        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

        }
    };

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showContent(List<GankItemBean> info, int page, int size) {
        AdapterUtils.setData(info, mAdapter, page, size, null);
        refreshLayout.setRefreshing(false);
    }
}
