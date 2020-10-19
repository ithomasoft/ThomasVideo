package com.thomas.video.core;

import android.content.res.Configuration;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.thomas.base.ui.BaseActivity;
import com.thomas.core.BarUtils;
import com.thomas.core.ColorUtils;

import butterknife.ButterKnife;

public abstract class AbstractActivity extends BaseActivity {


    @Override
    public void setContentView() {
        super.setContentView();
        ButterKnife.bind(this);
    }

    @Override
    public void initStatusBar() {
        BarUtils.setStatusBarColor(mActivity, ColorUtils.getColor(android.R.color.transparent));
        if (mActivity.getDelegate().getLocalNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            BarUtils.setStatusBarLightMode(mActivity, false);
        } else {
            BarUtils.setStatusBarLightMode(mActivity, true);
        }
    }

    @Override
    protected boolean isNeedAdapt() {
        return false;
    }

    @Override
    protected int setAdaptScreen() {
        return 360;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void onThomasClick(@NonNull View view) {

    }


}
