package com.thomas.video.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

public class LiveVideo extends JzvdStd {
    public LiveVideo(Context context) {
        super(context);
    }

    public LiveVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        SAVE_PROGRESS = false;
        titleTextView.setVisibility(View.INVISIBLE);
        currentTimeTextView.setVisibility(INVISIBLE);
        totalTimeTextView.setVisibility(INVISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }


    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        titleTextView.setVisibility(View.INVISIBLE);
        currentTimeTextView.setVisibility(INVISIBLE);
        totalTimeTextView.setVisibility(INVISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void gotoFullscreen() {
        super.gotoFullscreen();
        titleTextView.setVisibility(View.VISIBLE);
        currentTimeTextView.setVisibility(INVISIBLE);
        totalTimeTextView.setVisibility(INVISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void gotoNormalScreen() {
        super.gotoNormalScreen();
        titleTextView.setVisibility(View.INVISIBLE);
        currentTimeTextView.setVisibility(INVISIBLE);
        totalTimeTextView.setVisibility(INVISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int posterImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(topCon);
        bottomContainer.setVisibility(bottomCon);
        startButton.setVisibility(startBtn);
        loadingProgressBar.setVisibility(loadingPro);
        posterImageView.setVisibility(posterImg);
        bottomProgressBar.setVisibility(GONE);
        mRetryLayout.setVisibility(retryLayout);
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);

                if (screen != SCREEN_TINY) {
                    bottomProgressBar.setVisibility(View.GONE);
                }
                cancelProgressTimer();
            });
        }
    }


}
