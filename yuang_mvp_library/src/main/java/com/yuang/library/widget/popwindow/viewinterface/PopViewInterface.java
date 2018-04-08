package com.yuang.library.widget.popwindow.viewinterface;

import android.view.View;

import com.yuang.library.widget.popwindow.PopItemAction;
import com.yuang.library.widget.popwindow.PopWindow;

public interface PopViewInterface {

    void setTitleColor(int color);

    void setTitleTextSize(int textSize);

    void setMessageColor(int color);

    void setMessageTextSize(int textSize);

    void setTitleAndMessage(CharSequence title, CharSequence message);

    void addContentView(View view);

    void setPopWindow(PopWindow popWindow);

    void addItemAction(PopItemAction popItemAction);

    boolean showAble();

    void refreshBackground();

    void setIsShowLine(boolean isShowLine);

    void setIsShowCircleBackground(boolean isShow);
}
