package com.yuang.yuangapplication.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.yuang.library.base.BaseActivity;
import com.yuang.library.utils.SnackbarUtil;
import com.yuang.yuangapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BannerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;

    @Override
    public int getLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar,"Banner",true);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513333294097&di=ea18299e82126fc66df208fe6a3bdc47&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bc2fe74138d18367adab4e17e.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513333294096&di=879a68736af8a1c044df8d4fe73fdbdd&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F159%2F58%2FVL6QZKFYSFA8.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513333294096&di=a1d1cef2d4d4227b52fa7f2c8292cd3b&imgtype=0&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201410%2F2014102406.jpg");
        titles.add("美女1");
        titles.add("美女2");
        titles.add("美女3");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
//        banner.setMinimumHeight(getScreenWidth());
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                SnackbarUtil.showShort(getCurrentFocus().getRootView(), titles.get(position));
            }
        });
    }

    @Override
    protected void initData() {

    }

}
