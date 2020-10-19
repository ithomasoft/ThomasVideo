package com.thomas.video.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.thomas.video.R;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.ScaleConfig;

public class LoadingDialog extends BasePopupWindow {
    public LoadingDialog(Context context) {
        super(context);
        setBackPressEnable(false);
        setOutSideTouchable(false);
        setOutSideDismiss(false);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_loading);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation().withScale(ScaleConfig.CENTER).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation().withScale(ScaleConfig.CENTER).toDismiss();
    }
}
