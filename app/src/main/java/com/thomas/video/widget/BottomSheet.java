package com.thomas.video.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.thomas.core.ScreenUtils;
import com.thomas.video.R;

import razerdp.basepopup.BaseLazyPopupWindow;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

public class BottomSheet extends BasePopupWindow {
    private AppCompatTextView tvContent;
    private AppCompatImageView ivPic;

    public BottomSheet(Context context) {
        super(context);
        if (ScreenUtils.isLandscape()) {
            //横屏
            setMaxHeight(ScreenUtils.getScreenHeight() / 2);
            setMinHeight(ScreenUtils.getScreenHeight() / 3);
            setMaxWidth(ScreenUtils.getScreenHeight());
            setMinWidth(ScreenUtils.getScreenHeight());
        } else {
            //竖屏
            setMaxHeight(ScreenUtils.getScreenHeight() / 2);
            setMinHeight(ScreenUtils.getScreenHeight() / 3);
            setMaxWidth(ScreenUtils.getScreenWidth());
            setMinWidth(ScreenUtils.getScreenWidth());
        }

        tvContent = findViewById(R.id.tv_content);
        ivPic = findViewById(R.id.iv_pic);

    }


    public void setInfo(String content, String url) {
        tvContent.setText(content);
        Glide.with(getContext()).load(url).centerInside().into(ivPic);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM).toDismiss();
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        setAlignBackground(false);
        setClipChildren(false);
        setPopupGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_bottom_sheet);
    }
}
