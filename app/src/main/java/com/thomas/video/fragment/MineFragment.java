package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.core.AbstractLazyFragment;

import butterknife.BindView;

public class MineFragment extends AbstractLazyFragment {

    @BindView(R.id.btn_history)
    AppCompatTextView btnHistory;
    @BindView(R.id.btn_download)
    AppCompatTextView btnDownload;
    @BindView(R.id.btn_favorites)
    AppCompatTextView btnFavorites;
    @BindView(R.id.btn_settings)
    AppCompatTextView btnSettings;
    @BindView(R.id.btn_recommend)
    AppCompatTextView btnRecommend;
    @BindView(R.id.btn_disclaimer)
    AppCompatTextView btnDisclaimer;
    @BindView(R.id.btn_authority)
    AppCompatTextView btnAuthority;
    @BindView(R.id.btn_feedback)
    AppCompatTextView btnFeedback;
    @BindView(R.id.btn_about)
    AppCompatTextView btnAbout;
    @BindView(R.id.btn_open)
    AppCompatTextView btnOpen;
    @BindView(R.id.btn_share)
    AppCompatTextView btnShare;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    private MineFragment() {
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
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        applyThomasClickListener(btnHistory, btnDownload, btnFavorites,
                btnSettings, btnRecommend, btnDisclaimer, btnAuthority,
                btnFeedback, btnAbout, btnOpen, btnShare);
    }

    @Override
    public void onThomasClick(@NonNull View view) {
        if (view==btnHistory){
            ToastUtils.showShort("历史");
            return;
        }

        if (view==btnDownload){
            ToastUtils.showShort("下载");
            return;
        }

        if (view==btnFavorites){
            ToastUtils.showShort("收藏");
            return;
        }

        if (view==btnSettings){
            ToastUtils.showShort("设置中心");
            return;
        }

        if (view==btnOpen){
            ToastUtils.showShort("开源项目");
            return;
        }

        if (view==btnShare){
            ToastUtils.showShort("告诉朋友");
            return;
        }

        if (view==btnDisclaimer){
            ToastUtils.showShort("免责政策");
            return;
        }

        if (view==btnAuthority){
            ToastUtils.showShort("隐私政策");
            return;
        }

        if (view==btnAbout){
            ToastUtils.showShort("关于我们");
            return;
        }

        if (view==btnRecommend){
            ToastUtils.showShort("应用推荐");
            return;
        }

        if (view==btnFeedback){
            ToastUtils.showShort("意见反馈");
            return;
        }
    }
}
