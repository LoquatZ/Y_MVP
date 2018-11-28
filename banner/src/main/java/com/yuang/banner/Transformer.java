package com.yuang.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.yuang.banner.transformer.AccordionTransformer;
import com.yuang.banner.transformer.BackgroundToForegroundTransformer;
import com.yuang.banner.transformer.CubeInTransformer;
import com.yuang.banner.transformer.CubeOutTransformer;
import com.yuang.banner.transformer.DefaultTransformer;
import com.yuang.banner.transformer.DepthPageTransformer;
import com.yuang.banner.transformer.FlipHorizontalTransformer;
import com.yuang.banner.transformer.FlipVerticalTransformer;
import com.yuang.banner.transformer.ForegroundToBackgroundTransformer;
import com.yuang.banner.transformer.RotateDownTransformer;
import com.yuang.banner.transformer.RotateUpTransformer;
import com.yuang.banner.transformer.ScaleInOutTransformer;
import com.yuang.banner.transformer.StackTransformer;
import com.yuang.banner.transformer.TabletTransformer;
import com.yuang.banner.transformer.ZoomInTransformer;
import com.yuang.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
}
