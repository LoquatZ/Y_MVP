package com.yuang.yuangapplication.banner;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuang.banner.Banner;
import com.yuang.banner.BannerConfig;
import com.yuang.banner.MZBannerView;
import com.yuang.banner.Transformer;
import com.yuang.banner.listener.OnBannerListener;
import com.yuang.banner.loader.ImageLoaderInterface;
import com.yuang.library.base.BaseActivity;
import com.yuang.library.utils.SnackbarUtil;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.fragmentation.FragmentationActivity;
import com.yuang.yuangapplication.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BannerActivity extends BaseActivity {
    @BindView(R.id.bannerNew)
    MZBannerView bannerNew;
    @BindView(R.id.banner)
    Banner banner;

    @Override
    public int getLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar("Banner")
                .setBackground(R.drawable.bg_toolbar)
                .setLeftImage(R.mipmap.ic_back);
        initBannerOne();
        initBannerTWO();
    }

    private void initBannerOne() {
        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513333294097&di=ea18299e82126fc66df208fe6a3bdc47&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bc2fe74138d18367adab4e17e.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951352091&di=4dfde2acd1abcef2f2ca1b8df061864c&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F18d8bc3eb13533fa3d9640faa2d3fd1f40345bc6.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951352087&di=307f5f9cd1de0e692c6b5d5d26e46d89&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fthumb%2F808414%2Fbb7e4c438e8adaba807488fdd4eb7dd0_960x540.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951417409&di=f4dd2dca832721ec5e2006bdf762cb07&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Ff11f3a292df5e0feecb0a441576034a85fdf72c1.jpg");
        bannerNew.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                SnackbarUtil.showShort(getCurrentFocus().getRootView(), "位置：" + position);
            }
        });
        bannerNew.setPages(images);
        bannerNew.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                GlideApp.with(context).load(path).into((ImageView) imageView);
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });

    }

    private void initBannerTWO() {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513333294097&di=ea18299e82126fc66df208fe6a3bdc47&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bc2fe74138d18367adab4e17e.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951352091&di=4dfde2acd1abcef2f2ca1b8df061864c&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F18d8bc3eb13533fa3d9640faa2d3fd1f40345bc6.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951352087&di=307f5f9cd1de0e692c6b5d5d26e46d89&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fthumb%2F808414%2Fbb7e4c438e8adaba807488fdd4eb7dd0_960x540.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542951417409&di=f4dd2dca832721ec5e2006bdf762cb07&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Ff11f3a292df5e0feecb0a441576034a85fdf72c1.jpg");
        titles.add("美女1");
        titles.add("美女2");
        titles.add("美女3");
        titles.add("美女4");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                GlideApp.with(context).load(path).into((ImageView) imageView);
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
        //设置魅族动画效果
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
                startActivity(FragmentationActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
        bannerNew.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        bannerNew.pause();
    }
}
