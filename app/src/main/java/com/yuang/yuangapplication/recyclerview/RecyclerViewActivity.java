package com.yuang.yuangapplication.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
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
import com.yuang.library.glide.GlideCircleTransform;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.recyclerview.entity.TvBean;
import com.yuang.yuangapplication.utils.GlideApp;

import butterknife.BindView;

public class RecyclerViewActivity extends BaseActivity<RecyclerViewPresenter, RecyclerViewModel>
        implements RecyclerViewContract.View {

    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BaseQuickAdapter<TvBean.DataBean, BaseViewHolder> baseQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclear_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar, "RecyclerViewActivity", true);
        baseQuickAdapter = new BaseQuickAdapter<TvBean.DataBean, BaseViewHolder>(R.layout.item_tv_other) {
            @Override
            protected void convert(BaseViewHolder helper, TvBean.DataBean item) {
                //Glide在加载GridView等时,由于ImageView和Bitmap实际大小不符合,第一次时加载可能会变形(我这里出现了放大),必须在加载前再次固定ImageView大小
                GlideApp.with(mContext)
                        .load(item.getThumb())
                        .thumbnail(0.5f)
                        .transition(new DrawableTransitionOptions().crossFade(600))
                        .into((ImageView) helper.getView(R.id.thumnails));
                GlideApp.with(mContext)
                        .load(item.getAvatar())
                        .centerCrop()
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.ic_head));
                helper.setText(R.id.title, item.getTitle())
                        .setText(R.id.tv_viewnum, item.getView())
                        .setText(R.id.nickName, item.getNick());
            }
        };
        baseQuickAdapter.openLoadAnimation();
        baseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getData("json/categories/lol/list.json");
            }
        },recyclerview);

        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerview.setSwipeMenuCreator(swipeMenuCreator);
        recyclerview.setSwipeMenuItemClickListener(mMenuItemClickListener);
        recyclerview.setAdapter(baseQuickAdapter);

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
    public void showContent(TvBean info) {
        baseQuickAdapter.addData(info.getData());
    }

}
