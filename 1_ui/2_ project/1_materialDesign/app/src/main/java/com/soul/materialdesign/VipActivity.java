package com.soul.materialdesign;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;

import com.soul.materialdesign.utils.SystemBarHelper;
import com.soul.materialdesign.view.BaseActivity;

import butterknife.BindView;

public class VipActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //允许transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //淡入动画
        getWindow().setEnterTransition(new Fade());
        //滑动动画
        //        getWindow().setEnterTransition(new Slide());
        //        //分解动画
        //        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        mWebView.loadUrl("http://vip.bilibili.com/site/vip-faq-h5.html#yv1");
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
