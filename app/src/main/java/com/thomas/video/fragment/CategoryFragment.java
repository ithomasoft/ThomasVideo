package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;
import com.thomas.core.ActivityUtils;
import com.thomas.video.R;
import com.thomas.video.core.AbstractLazyFragment;
import com.thomas.video.ui.LiveActivity;
import com.thomas.video.ui.TopicActivity;

import butterknife.BindView;

public class CategoryFragment extends AbstractLazyFragment {

    @BindView(R.id.btn_movie)
    MaterialCardView btnMovie;
    @BindView(R.id.btn_teleplay)
    MaterialCardView btnTeleplay;
    @BindView(R.id.btn_variety)
    MaterialCardView btnVariety;
    @BindView(R.id.btn_comic)
    MaterialCardView btnComic;
    @BindView(R.id.btn_six_room)
    MaterialCardView btnSixRoom;
    @BindView(R.id.btn_record)
    MaterialCardView btnRecord;
    @BindView(R.id.btn_tv)
    MaterialCardView btnLive;
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    private CategoryFragment() {
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
        return R.layout.fragment_category;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        applyThomasClickScaleListener(btnMovie,btnLive, btnTeleplay, btnComic,
                btnSixRoom, btnRecord, btnVariety);
    }

    @Override
    public void onThomasClick(@NonNull View view) {
        Bundle bundle = new Bundle();
        if (view == btnRecord) {
            bundle.putString("type", "36");
            bundle.putString("title", "电影解说");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }
        if (view == btnMovie) {
            bundle.putString("type", "1");
            bundle.putString("title", "电影");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }
        if (view == btnTeleplay) {
            bundle.putString("type", "2");
            bundle.putString("title", "电视");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }
        if (view == btnVariety) {
            bundle.putString("type", "3");
            bundle.putString("title", "综艺");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }

        if (view == btnComic) {
            bundle.putString("type", "4");
            bundle.putString("title", "动漫");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }

        if (view == btnSixRoom) {
            bundle.putString("type", "34");
            bundle.putString("title", "福利");
            ActivityUtils.startActivity(bundle, TopicActivity.class);
            return;
        }

        if (view == btnLive) {
            ActivityUtils.startActivity(LiveActivity.class);
            return;
        }
    }
}
