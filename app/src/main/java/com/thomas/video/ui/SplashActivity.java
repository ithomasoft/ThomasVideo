package com.thomas.video.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.thomas.core.ActivityUtils;
import com.thomas.core.ThreadUtils;
import com.thomas.video.R;
import com.thomas.video.core.AbstractActivity;

public class SplashActivity extends AbstractActivity {

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {


    }

    @Override
    public void doBusiness() {
        ThreadUtils.runOnUiThreadDelayed(() -> {
            ActivityUtils.startActivity(HomeActivity.class);
            ActivityUtils.finishActivity(mActivity);
        }, 2000);
    }
}