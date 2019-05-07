package com.soul.screen;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCutout();
        Density.setDensity(getApplication(), this);
        setContentView(R.layout.activity_main);
    }

    /**
     * 设置刘海屏
     */
    private void setCutout() {
        final Window window = getWindow();
        //1 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ///1 判断手机厂商，2 判断手机是否刘海 3 设置是否让内容区域延伸进刘海  4 设置控件是否避开刘海区域 5 获取刘海的高度
        // 判断手机是否是刘海屏
        final boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout) {
            //2 让内容区域延伸进刘海
            final WindowManager.LayoutParams params = window.getAttributes();
            /**
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容去延伸进刘海区
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
             */
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);

        }
        //3 设置成沉浸式
        int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        int visibility = window.getDecorView().getSystemUiVisibility();
        visibility |= flags;
        window.getDecorView().setSystemUiVisibility(visibility);
    }


    private boolean hasDisplayCutout(Window window) {
        DisplayCutout displayCutout;
        final View rootView = window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final WindowInsets insets = rootView.getRootWindowInsets();
            if (insets != null) {
                displayCutout = insets.getDisplayCutout();
                if (displayCutout != null) {
                    if (displayCutout.getBoundingRects() != null
                            && displayCutout.getBoundingRects().size() > 0
                            && displayCutout.getSafeInsetTop() > 0) {
                        return true;
                    }
                }
            }

        }
        return false;
    }


}
