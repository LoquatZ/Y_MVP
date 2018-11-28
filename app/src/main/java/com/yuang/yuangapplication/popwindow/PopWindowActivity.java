package com.yuang.yuangapplication.popwindow;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuang.library.base.BaseActivity;
import com.yuang.library.widget.pop_window.PopItemAction;
import com.yuang.library.widget.pop_window.PopWindow;
import com.yuang.yuangapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PopWindowActivity extends BaseActivity {
    @BindView(R.id.pop)
    Button pop;
    @BindView(R.id.bottom_pop)
    Button bottomPop;
    private View customView;
    private PopWindow popWindow;
    private BaseQuickAdapter<String, BaseViewHolder> popAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pop_window;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar("PopWindow")
                .setBackground(R.drawable.bg_toolbar)
                .setLeftImage(R.mipmap.ic_back);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.pop, R.id.bottom_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pop:
                customView = View.inflate(this, R.layout.item_popwindow, null);
                popWindow = new PopWindow.Builder(this)
                        .setStyle(PopWindow.PopWindowStyle.PopDown)
                        .setView(customView)
                        .create();
                RecyclerView pop_recyclerview = customView.findViewById(R.id.pop_recyclerview);
                popAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_pop_list) {
                    @Override
                    protected void convert(BaseViewHolder helper, String item) {
                        helper.setText(R.id.name, item);
                    }
                };
                pop_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                pop_recyclerview.setAdapter(popAdapter);
                popAdapter.setNewData(getPopBean());
                popWindow.show(view);
                break;
            case R.id.bottom_pop:
                PopWindow popWindow = new PopWindow.Builder(this)
                        .setStyle(PopWindow.PopWindowStyle.PopUp)
                        .setTitle("标题")
                        .addItemAction(new PopItemAction("选项1", PopItemAction.PopItemStyle.Normal, () -> {

                        }))
                        .addItemAction(new PopItemAction("选项2", PopItemAction.PopItemStyle.Normal, () -> {

                        }))
                        .addItemAction(new PopItemAction("选项3", PopItemAction.PopItemStyle.Normal, () -> {

                        }))
                        .addItemAction(new PopItemAction("选项4", PopItemAction.PopItemStyle.Normal, () -> {

                        }))
                        .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                        .create();
                popWindow.show();
                break;
        }
    }


    private List<String> getPopBean() {
        List<String> typeList = new ArrayList<>();
        typeList.add("类型1");
        typeList.add("类型2");
        typeList.add("类型3");
        typeList.add("类型4");
        typeList.add("类型5");
        typeList.add("类型6");
        typeList.add("类型7");
        typeList.add("类型8");
        return typeList;
    }
}
