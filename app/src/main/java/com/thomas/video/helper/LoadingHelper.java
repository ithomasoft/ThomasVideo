package com.thomas.video.helper;

import com.thomas.core.ActivityUtils;
import com.thomas.video.widget.LoadingDialog;

public class LoadingHelper {


    private static LoadingDialog loadingDialog;

    public static void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(ActivityUtils.getTopActivity());
        }
        loadingDialog.showPopupWindow();
    }

    public static void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
