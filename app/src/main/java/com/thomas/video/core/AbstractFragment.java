package com.thomas.video.core;

import android.view.View;

import androidx.annotation.NonNull;

import com.thomas.base.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    public boolean isTransparent() {
        return true;
    }


    @Override
    public void setContentView() {
        super.setContentView();
        unbinder = ButterKnife.bind(this, mContentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onThomasClick(@NonNull View view) {

    }
}
