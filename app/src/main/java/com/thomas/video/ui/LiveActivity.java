package com.thomas.video.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.thomas.core.ActivityUtils;
import com.thomas.core.BarUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.LiveAdapter;
import com.thomas.video.core.AbstractActivity;
import com.thomas.video.data.LiveData;
import com.thomas.video.media.ExoMedia;
import com.thomas.video.widget.FooterView;
import com.thomas.video.widget.LiveVideo;
import com.thomas.video.widget.ScreenRotateUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class LiveActivity extends AbstractActivity implements ScreenRotateUtils.OrientationChangeListener {

    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.player)
    LiveVideo player;
    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;

    private LiveAdapter mAdapter;
    private JZDataSource mJzDataSource;

    private List<LiveData> datas = new ArrayList<>();
    private int playingPosition = 0;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_live;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    ActivityUtils.finishActivity(mActivity, true);
                }
            }
        });
        BarUtils.setStatusBarLightMode(mActivity, false);
        ScreenRotateUtils.getInstance(mActivity).setOrientationChangeListener(this);
        mAdapter = new LiveAdapter();
        rvChannel.setLayoutManager(new GridLayoutManager(mActivity, 3));
        rvChannel.setAdapter(mAdapter);
        mAdapter.setFooterView(new FooterView(mActivity));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (playingPosition != position) {
                    playingPosition = position;
                    playLive(mAdapter.getItem(position));
                }
            }
        });
    }

    @Override
    public void doBusiness() {
        datas.add(new LiveData("CCTV-1", "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"));
        datas.add(new LiveData("CCTV-2", "http://ivi.bupt.edu.cn/hls/cctv2hd.m3u8"));
        datas.add(new LiveData("CCTV-3", "http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8"));
        datas.add(new LiveData("CCTV-4", "http://ivi.bupt.edu.cn/hls/cctv4hd.m3u8"));
        datas.add(new LiveData("CCTV-5+", "http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8"));
        datas.add(new LiveData("CCTV-6", "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"));
        datas.add(new LiveData("CCTV-7", "http://ivi.bupt.edu.cn/hls/cctv7hd.m3u8"));
        datas.add(new LiveData("CCTV-8", "http://ivi.bupt.edu.cn/hls/cctv8hd.m3u8"));
        datas.add(new LiveData("CCTV-9", "http://ivi.bupt.edu.cn/hls/cctv9hd.m3u8"));
        datas.add(new LiveData("CCTV-10", "http://ivi.bupt.edu.cn/hls/cctv10hd.m3u8"));
        datas.add(new LiveData("CCTV-12", "http://ivi.bupt.edu.cn/hls/cctv12hd.m3u8"));
        datas.add(new LiveData("CCTV-14", "http://ivi.bupt.edu.cn/hls/cctv14hd.m3u8"));
        datas.add(new LiveData("CCTV-17", "http://ivi.bupt.edu.cn/hls/cctv17hd.m3u8"));
        datas.add(new LiveData("CETV-1", "http://ivi.bupt.edu.cn/hls/cetv1hd.m3u8"));
        datas.add(new LiveData("CGTN", "http://ivi.bupt.edu.cn/hls/cgtnhd.m3u8"));
        datas.add(new LiveData("CGTN-DOC", "http://ivi.bupt.edu.cn/hls/cgtndochd.m3u8"));
        datas.add(new LiveData("CHC", "http://ivi.bupt.edu.cn/hls/chchd.m3u8"));

        datas.add(new LiveData("北京卫视", "http://ivi.bupt.edu.cn/hls/btv1hd.m3u8"));
        datas.add(new LiveData("北京文艺", "http://ivi.bupt.edu.cn/hls/btv2hd.m3u8"));
        datas.add(new LiveData("北京影视", "http://ivi.bupt.edu.cn/hls/btv4hd.m3u8"));
        datas.add(new LiveData("东奥纪实", "http://ivi.bupt.edu.cn/hls/btv11hd.m3u8"));

        datas.add(new LiveData("湖南卫视", "http://ivi.bupt.edu.cn/hls/hunanhd.m3u8"));
        datas.add(new LiveData("江苏卫视", "http://ivi.bupt.edu.cn/hls/jshd.m3u8"));
        datas.add(new LiveData("东方卫视", "http://ivi.bupt.edu.cn/hls/dfhd.m3u8"));
        datas.add(new LiveData("安徽卫视", "http://ivi.bupt.edu.cn/hls/ahhd.m3u8"));
        datas.add(new LiveData("黑龙江卫视", "http://ivi.bupt.edu.cn/hls/hljhd.m3u8"));
        datas.add(new LiveData("辽宁卫视", "http://ivi.bupt.edu.cn/hls/lnhd.m3u8"));
        datas.add(new LiveData("深圳卫视", "http://ivi.bupt.edu.cn/hls/szhd.m3u8"));
        datas.add(new LiveData("广东卫视", "http://ivi.bupt.edu.cn/hls/gdhd.m3u8"));
        datas.add(new LiveData("天津卫视", "http://ivi.bupt.edu.cn/hls/tjhd.m3u8"));
        datas.add(new LiveData("湖北卫视", "http://ivi.bupt.edu.cn/hls/hbhd.m3u8"));
        datas.add(new LiveData("山东卫视", "http://ivi.bupt.edu.cn/hls/sdhd.m3u8"));
        datas.add(new LiveData("重庆卫视", "http://ivi.bupt.edu.cn/hls/cqhd.m3u8"));
        datas.add(new LiveData("上海纪实", "http://ivi.bupt.edu.cn/hls/docuchina.m3u8"));
        datas.add(new LiveData("金鹰纪实", "http://ivi.bupt.edu.cn/hls/gedocu.m3u8"));
        datas.add(new LiveData("东南卫视", "http://ivi.bupt.edu.cn/hls/dnhd.m3u8"));
        datas.add(new LiveData("四川卫视", "http://ivi.bupt.edu.cn/hls/schd.m3u8"));
        datas.add(new LiveData("河北卫视", "http://ivi.bupt.edu.cn/hls/hebhd.m3u8"));
        datas.add(new LiveData("江西卫视", "http://ivi.bupt.edu.cn/hls/jxhd.m3u8"));
        datas.add(new LiveData("河南卫视", "http://ivi.bupt.edu.cn/hls/hnhd.m3u8"));
        datas.add(new LiveData("广西卫视", "http://ivi.bupt.edu.cn/hls/gxhd.m3u8"));
        datas.add(new LiveData("吉林卫视", "http://ivi.bupt.edu.cn/hls/jlhd.m3u8"));
        datas.add(new LiveData("海南卫视", "http://ivi.bupt.edu.cn/hls/lyhd.m3u8"));
        datas.add(new LiveData("贵州卫视", "http://ivi.bupt.edu.cn/hls/gzhd.m3u8"));

        mAdapter.setNewInstance(datas);

        playLive(datas.get(0));

    }

    private void playLive(LiveData liveData) {
        titleBar.getCenterTextView().setText(liveData.getTitle());
        mJzDataSource = new JZDataSource(liveData.getPlayUrl(), liveData.getTitle());
        player.setUp(mJzDataSource, JzvdStd.SCREEN_NORMAL);
        player.startVideo();
    }

    @Override
    public void orientationChange(int orientation) {
        if (Jzvd.CURRENT_JZVD != null
                && (player.state == Jzvd.STATE_PLAYING || player.state == Jzvd.STATE_PAUSE)
                && player.screen != Jzvd.SCREEN_TINY) {
            if (orientation >= 45 && orientation <= 315 && player.screen == Jzvd.SCREEN_NORMAL) {
                changeScreenFullLandscape(ScreenRotateUtils.orientationDirection);
            } else if (((orientation >= 0 && orientation < 45) || orientation > 315) && player.screen == Jzvd.SCREEN_FULLSCREEN) {
                changeScrenNormal();
            }
        }
    }

    @Override
    protected void onResume() {
        Jzvd.goOnPlayOnResume();
        super.onResume();
        ScreenRotateUtils.getInstance(this).start(this);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenRotateUtils.getInstance(this).stop();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        ScreenRotateUtils.getInstance(mActivity).setOrientationChangeListener(null);
    }


    /**
     * 竖屏并退出全屏
     */
    private void changeScrenNormal() {
        if (player != null && player.screen == Jzvd.SCREEN_FULLSCREEN) {
            player.autoQuitFullscreen();
        }
    }

    /**
     * 横屏
     */
    private void changeScreenFullLandscape(float x) {
        //从竖屏状态进入横屏
        if (player != null && player.screen != Jzvd.SCREEN_FULLSCREEN) {
            if ((System.currentTimeMillis() - Jzvd.lastAutoFullscreenTime) > 2000) {
                player.autoFullscreen(x);
                Jzvd.lastAutoFullscreenTime = System.currentTimeMillis();
            }
        }
    }


}
