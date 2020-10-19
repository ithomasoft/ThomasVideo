package com.thomas.video.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thomas.core.ColorUtils;
import com.thomas.core.SizeUtils;
import com.thomas.video.R;

import java.util.Timer;
import java.util.TimerTask;

public class VideoSpeedPopup extends PopupWindow implements View.OnClickListener {
    private static final int COMPLETED = 0;
    protected DismissTimerTask mDismissTimerTask;
    private TextView speedOne, speedTwo, speedThree, speedFour, speedFive;
    private SpeedChangeListener speedChangeListener;
    private Context mC;
    private LayoutInflater inflater;
    private View contentView;
    private Timer mDismissTimer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                dismiss();

            }
        }
    };

    public VideoSpeedPopup(Context context) {
        super(context);
        mC = context;
        inflater = (LayoutInflater) mC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_video_speed, null);
        setContentView(contentView);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(SizeUtils.dp2px(200));
        speedOne = contentView.findViewById(R.id.pop_speed_1);
        speedTwo = contentView.findViewById(R.id.pop_speed_1_25);
        speedThree = contentView.findViewById(R.id.pop_speed_1_5);
        speedFour = contentView.findViewById(R.id.pop_speed_1_75);
        speedFive = contentView.findViewById(R.id.pop_speed_2);
        setOutsideTouchable(true);
        //不设置该属性，弹窗于屏幕边框会有缝隙并且背景不是半透明
        setBackgroundDrawable(new BitmapDrawable());
        speedOne.setOnClickListener(this);
        speedTwo.setOnClickListener(this);
        speedThree.setOnClickListener(this);
        speedFour.setOnClickListener(this);
        speedFive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (speedChangeListener != null) {
            switch (v.getId()) {
                case R.id.pop_speed_1:
                    speedOne.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
                    speedTwo.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedThree.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFour.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFive.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedChangeListener.speedChange(1f);
                    break;
                case R.id.pop_speed_1_25:
                    speedOne.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedTwo.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
                    speedThree.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFour.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFive.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedChangeListener.speedChange(1.25f);
                    break;
                case R.id.pop_speed_1_5:
                    speedOne.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedTwo.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedThree.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
                    speedFour.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFive.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedChangeListener.speedChange(1.5f);
                    break;
                case R.id.pop_speed_1_75:
                    speedOne.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedTwo.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedThree.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFour.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
                    speedFive.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedChangeListener.speedChange(1.75f);
                    break;
                case R.id.pop_speed_2:
                    speedOne.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedTwo.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedThree.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFour.setTextColor(ColorUtils.getColor(android.R.color.white));
                    speedFive.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
                    speedChangeListener.speedChange(2f);
                    break;
            }
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        startDismissTimer();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        cancelDismissTimer();
    }

    public void startDismissTimer() {
        cancelDismissTimer();
        mDismissTimer = new Timer();
        mDismissTimerTask = new DismissTimerTask();
        mDismissTimer.schedule(mDismissTimerTask, 2500);
    }

    public void cancelDismissTimer() {
        if (mDismissTimer != null) {
            mDismissTimer.cancel();
        }
        if (mDismissTimerTask != null) {
            mDismissTimerTask.cancel();
        }

    }

    public SpeedChangeListener getSpeedChangeListener() {
        return speedChangeListener;
    }

    public void setSpeedChangeListener(SpeedChangeListener speedChangeListener) {
        this.speedChangeListener = speedChangeListener;
    }

    public interface SpeedChangeListener {
        void speedChange(float speed);
    }

    public class DismissTimerTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = COMPLETED;
            handler.sendMessage(message);
        }
    }
}
