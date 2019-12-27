package com.yuang.yuangapplication.float_menu;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.yuang.floatmenu.view.FloatItem;
import com.yuang.floatmenu.view.FloatLogoMenu;
import com.yuang.floatmenu.view.FloatMenuView;
import com.yuang.library.rx.RxManager;
import com.yuang.yuangapplication.App;
import com.yuang.yuangapplication.R;

import java.util.ArrayList;

public class FlowMenuService extends Service {

    private FloatLogoMenu mFloatMenu;

    private String HOME = "返回应用";
    private String FEEDBACK = "快速截图";
    private String[] MENU_ITEMS = {HOME, FEEDBACK};

    private int[] menuIcons = new int[]{R.drawable.yw_menu_account, R.drawable.ic_cat};
    ArrayList<FloatItem> itemList = new ArrayList<>();
    public RxManager mRxManager = new RxManager();
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createView();
    }

    private void createView() {
        for (int i = 0; i < menuIcons.length; i++) {
            itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), String.valueOf(i + 1)));
        }
        mFloatMenu = new FloatLogoMenu.Builder()
//                    .withActivity(mActivity)
                .withContext(App.getInstance())//这个在7.0（包括7.0）以上以及大部分7.0以下的国产手机上需要用户授权，需要搭配<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
                .logo(BitmapFactory.decodeResource(getResources(), R.drawable.ic_float_logo))
                .drawCicleMenuBg(false)
                .backMenuColor(0xffe4e3e1)
                .setBgDrawable(this.getResources().getDrawable(R.drawable.yw_game_float_menu_bg))
                //这个背景色需要和logo的背景色一致
                .setFloatItems(itemList)
                .defaultLocation(FloatLogoMenu.RIGHT)
                .drawRedPointNum(false)
                .showWithListener(new FloatMenuView.OnMenuClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onItemClick(int position, String title) {
                        switch (position) {
                            case 0:
                                try {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    ComponentName cmp = new ComponentName("com.yuang.yuangapplication", "com.yuang.yuangapplication.main.MainActivity");
                                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setComponent(cmp);
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1:

                                break;
                        }
                    }

                    @Override
                    public void dismiss() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
        destroyFloat();
    }

    public void destroyFloat() {
        if (mFloatMenu != null) {
            mFloatMenu.destoryFloat();
        }
        mFloatMenu = null;
    }
}
