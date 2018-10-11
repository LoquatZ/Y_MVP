package com.yuang.library.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yuang.library.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yuang on 2018/4/4.
 * Summary:
 */

public class YUtils {
    private static final Object mLock = new Object();
    private static YUtils mInstance;
    public static YUtils getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new YUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 图片裁剪
     *
     * @param uri
     */
    public void initUCrop(Activity activity, Uri uri) {
        //Uri destinationUri = RxPhotoUtils.createImagePathUri(this);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(activity.getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.u_crop_toolbar_color));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.u_crop_status_bar_color));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
//        opt`ions.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
//        options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
//        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
//        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
//        options.setCropGridColumnCount(2);
        //设置横线的数量
//        options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(9, 16)
                .withMaxResultSize(1080, 1920)
                .withOptions(options)
                .start(activity);
    }

}
