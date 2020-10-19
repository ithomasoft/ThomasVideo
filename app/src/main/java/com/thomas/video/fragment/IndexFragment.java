package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.thomas.video.R;
import com.thomas.video.core.AbstractLazyFragment;

public class IndexFragment extends AbstractLazyFragment {

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    private IndexFragment() {
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {

    }
}
