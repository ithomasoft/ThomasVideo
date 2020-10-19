package com.thomas.video.core;

import android.view.View;

import androidx.annotation.NonNull;

import com.thomas.base.ui.LazyFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractLazyFragment extends LazyFragment {

    private Unbinder unbinder;

    @Override
    protected void destroyViewAndThing() {

    }

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
