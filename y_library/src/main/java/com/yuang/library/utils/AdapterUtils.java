package com.yuang.library.utils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuang.library.R;

import java.util.List;

/**
 * 类描述: Adapter工具类
 * 创建人: Yuang
 * 创建时间: 2018/11/20 3:32 PM
 */
public class AdapterUtils {

    /**
     * Adapter 设置数据
     *
     * @param <T>     泛型
     * @param list    数据源
     * @param adapter 适配器
     * @param page    页数
     * @param size    个数
     */
    public static <T> void setData(List<T> list, BaseQuickAdapter adapter, int page, int size) {
        Preconditions.checkNotNull(adapter);
        if (list == null || list.size() == 0) {
            if (page != 1) {
                adapter.loadMoreEnd();
            } else {
                setEmptyView(adapter);
            }
        } else {
            if (page == 1) {
                adapter.setNewData(list);
            } else {
                adapter.addData(list);
            }
            if (list.size() < size) {
                adapter.loadMoreEnd();
            } else {
                adapter.loadMoreComplete();
            }
        }
    }

    /**
     * 设置空数据页面
     *
     * @param adapter
     */
    public static void setEmptyView(BaseQuickAdapter adapter) {
        Preconditions.checkNotNull(adapter);
        adapter.setEmptyView(R.layout.item_empty_view);
    }
}
