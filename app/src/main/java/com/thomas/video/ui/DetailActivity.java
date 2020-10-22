package com.thomas.video.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.thomas.core.BarUtils;
import com.thomas.core.ColorUtils;
import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.PersonAdapter;
import com.thomas.video.core.AbstractActivity;
import com.thomas.video.data.EpisodeData;
import com.thomas.video.data.OKData;
import com.thomas.video.media.ExoMedia;
import com.thomas.video.widget.BottomSheet;
import com.thomas.video.widget.ScreenRotateUtils;
import com.thomas.video.widget.SuperVideo;
import com.thomas.video.widget.VideoEpisodePopup;
import com.thomas.video.widget.VideoSpeedPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class DetailActivity extends AbstractActivity implements SuperVideo.SuperVideoListener, ScreenRotateUtils.OrientationChangeListener, VideoEpisodePopup.EpisodeClickListener, VideoSpeedPopup.SpeedChangeListener {
    @BindView(R.id.player)
    SuperVideo player;
    @BindView(R.id.tab_episode)
    TabLayout tabEpisode;
    @BindView(R.id.tv_video_name)
    AppCompatTextView tvVideoName;
    @BindView(R.id.tv_video_score)
    AppCompatTextView tvVideoScore;
    @BindView(R.id.tv_video_type)
    AppCompatTextView tvVideoType;
    @BindView(R.id.tv_video_time)
    AppCompatTextView tvVideoTime;
    @BindView(R.id.tv_video_blurb)
    AppCompatTextView tvVideoBlurb;
    @BindView(R.id.rv_guide)
    RecyclerView rvGuide;
    @BindView(R.id.ll_guide)
    LinearLayoutCompat llGuide;
    @BindView(R.id.rv_actor)
    RecyclerView rvActor;
    @BindView(R.id.ll_actor)
    LinearLayoutCompat llActor;

    BottomSheet bottomSheet;

    //倍数弹窗
    private VideoSpeedPopup videoSpeedPopup;
    private VideoEpisodePopup videoEpisodePopup;


    private JZDataSource mJzDataSource;
    private OKData.ListBean data;
    private List<EpisodeData> episodes = new ArrayList<>();

    private int playingPosition = 0;

    private PersonAdapter guideAdapter, actorAdapter;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        data = (OKData.ListBean) bundle.getSerializable("data");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        BarUtils.setStatusBarLightMode(mActivity, false);
        bottomSheet = new BottomSheet(mActivity);
        applyThomasClickListener(tvVideoBlurb);
        ScreenRotateUtils.getInstance(mActivity).setOrientationChangeListener(this);
        makeEpisodes();
        setEpisodes();
        player.setSuperVideoListener(this);
        tvVideoName.setText(data.getVod_name() + (TextUtils.isEmpty(data.getVod_sub()) ? " " : " (" + data.getVod_sub() + ")"));
        tvVideoScore.setText(data.getVod_score());
        tvVideoType.setText(data.getType_name() + (TextUtils.isEmpty(data.getVod_area()) ? "" : " | ") + data.getVod_area() + (TextUtils.isEmpty(data.getVod_lang()) ? "" : " | ") + data.getVod_lang() + (TextUtils.isEmpty(data.getVod_year()) ? "" : " | ") + data.getVod_year() + (TextUtils.isEmpty(data.getVod_remarks()) ? "" : " | ") + data.getVod_remarks());
        tvVideoTime.setText("更新时间：" + data.getVod_time().split(" ")[0]);
        tvVideoBlurb.setText(data.getVod_blurb() + " 查看更多>>");
        if (TextUtils.isEmpty(data.getVod_director())) {
            llGuide.setVisibility(View.GONE);
        } else {
            llGuide.setVisibility(View.VISIBLE);
            guideAdapter = new PersonAdapter();
            rvGuide.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
            rvGuide.setAdapter(guideAdapter);
            guideAdapter.setNewInstance(Arrays.asList(data.getVod_director().split(",")));
        }

        if (TextUtils.isEmpty(data.getVod_actor())) {
            llActor.setVisibility(View.GONE);
        } else {
            llActor.setVisibility(View.VISIBLE);
            actorAdapter = new PersonAdapter();
            rvActor.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
            rvActor.setAdapter(actorAdapter);
            actorAdapter.setNewInstance(Arrays.asList(data.getVod_actor().split(",")));
        }

//        if (guideAdapter != null) {
//            guideAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                    toSearch(guideAdapter.getData().get(position));
//                }
//            });
//        }
//
//        if (actorAdapter != null) {
//            actorAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                    toSearch(actorAdapter.getData().get(position));
//                }
//            });
//        }

    }

//    private void toSearch(String key) {
//        Bundle bundle = new Bundle();
//        bundle.putString("content", key);
//        ActivityUtils.startActivity(bundle, ResultActivity.class);
//    }

    private void setEpisodes() {
        tabEpisode.clearOnTabSelectedListeners();
        tabEpisode.removeAllTabs();
        for (int i = 0; i < episodes.size(); i++) {
            tabEpisode.addTab(tabEpisode.newTab().setText(episodes.get(i).getVideoName()));
        }

        for (int i = 0; i < tabEpisode.getTabCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = tabEpisode.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            tabAt.setCustomView(R.layout.item_content);
            //通过tab对象找到自定义视图的ID
            TextView textView = tabAt.getCustomView().findViewById(R.id.tv_content);
            //设置tab上的文字
            textView.setText(tabEpisode.getTabAt(i).getText());
            int current = playingPosition;
            if (i == current) {
                //选中后字体
                textView.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
            } else {
                textView.setTextColor(ColorUtils.getColor(R.color.colorText));
            }
        }

        tabEpisode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                EpisodeData entity = episodes.get(tab.getPosition());
                mJzDataSource = new JZDataSource(entity.getVideoUrl(), data.getVod_name() + " " + entity.getVideoName());
                updateTabView(tab, true);
                playChangeUrl();
                isNext(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        isNext(tabEpisode.getSelectedTabPosition());
    }

    private void makeEpisodes() {
        String[] playPlatform = data.getVod_play_from().split(data.getVod_play_note());
        ArrayList<String> platform = new ArrayList(playPlatform.length);
        Collections.addAll(platform, playPlatform);
        int ckm3u8Position = platform.indexOf("ckm3u8");
        String playerUrl = data.getVod_play_url().split(data.getVod_play_note())[ckm3u8Position];
        if (playerUrl.contains("#")) {
            tabEpisode.setVisibility(View.VISIBLE);
            //说明有多集
            episodes.clear();
            String[] episodeTemp = playerUrl.split("#");
            ArrayList<String> temp = new ArrayList(episodeTemp.length);
            Collections.addAll(temp, episodeTemp);
            for (String str : temp) {
                EpisodeData episodeData = new EpisodeData(str.split("@")[0], str.split("@")[1]);
                episodes.add(episodeData);
            }
        } else {
            tabEpisode.setVisibility(View.GONE);
            EpisodeData episodeData = new EpisodeData(playerUrl.split("@")[0], playerUrl.split("@")[1]);
            episodes.clear();
            episodes.add(episodeData);
        }
    }

    @Override
    public void onThomasClick(@NonNull View view) {
        if (view == tvVideoBlurb) {
            bottomSheet.setInfo(data.getVod_content(), data.getVod_pic());
            bottomSheet.showPopupWindow();
        }
    }

    @Override
    public void doBusiness() {
        mJzDataSource = new JZDataSource(episodes.get(0).getVideoUrl(), data.getVod_name() + " " + episodes.get(0).getVideoName());
        player.setUp(mJzDataSource, JzvdStd.SCREEN_NORMAL, ExoMedia.class);
        player.startVideo();
    }

    @Override
    public void nextClick() {
        int position = tabEpisode.getSelectedTabPosition() + 1;
        EpisodeData entity = episodes.get(position);
        mJzDataSource = new JZDataSource(entity.getVideoUrl(), data.getVod_name() + " " + entity.getVideoName());
        TabLayout.Tab tab = tabEpisode.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public void backClick() {
        if (player.screen == SuperVideo.SCREEN_FULLSCREEN) {
            dismissSpeedPopAndEpisodePop();
            SuperVideo.backPress();
        } else {
            finish();
        }
    }

    @Override
    public void throwingScreenClick() {
        ToastUtils.showShort("投屏");
    }

    @Override
    public void selectPartsClick() {
        if (videoEpisodePopup == null) {
            videoEpisodePopup = new VideoEpisodePopup(this, episodes);
            videoEpisodePopup.setEpisondeClickListener(this);
        }
        videoEpisodePopup.setPlayNum(tabEpisode.getSelectedTabPosition()+1);
        videoEpisodePopup.showAtLocation(getWindow().getDecorView(), Gravity.RIGHT, 0, 0);
    }

    @Override
    public void speedClick() {
        if (videoSpeedPopup == null) {
            videoSpeedPopup = new VideoSpeedPopup(this);
            videoSpeedPopup.setSpeedChangeListener(this);
        }
        videoSpeedPopup.showAtLocation(getWindow().getDecorView(), Gravity.RIGHT, 0, 0);
    }


    /**
     * 用来改变tabLayout选中后的字体大小及颜色
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        TextView tv_tab = tab.getCustomView().findViewById(R.id.tv_content);
        if (isSelect) {
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体
            tv_tab.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
        } else {
            //设置标签取消选中
            tv_tab.setSelected(false);
            //恢复为默认字体
            tv_tab.setTextColor(ColorUtils.getColor(R.color.colorText));
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
     * 判断是否有下一集
     */
    private void isNext(int position) {
        //判断是否还有下一集
        if (position == (episodes.size() - 1)) {
            player.changeNextBottonUi(false);
        } else {
            player.changeNextBottonUi(true);
        }
    }

    /**
     * 更换播放地址
     */
    private void playChangeUrl() {
        long progress = 0;
        player.changeUrl(mJzDataSource, progress);
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


    /**
     * 关闭倍速播放弹窗和选集弹窗
     */
    private void dismissSpeedPopAndEpisodePop() {
        if (videoSpeedPopup != null) {
            videoSpeedPopup.dismiss();
        }
        if (videoEpisodePopup != null) {
            videoEpisodePopup.dismiss();
        }
    }

    /**
     * 改变播放倍速
     *
     * @param speed
     */
    private void changeSpeed(float speed) {
        Object[] object = {speed};
        player.mediaInterface.setSpeed(speed);
        mJzDataSource.objects[0] = object;
        ToastUtils.showShort("正在以" + speed + "X倍速播放");
        player.speedChange(speed);
    }

    @Override
    public void onEpisodeClickListener(EpisodeData entity, int position) {
        TabLayout.Tab tab = tabEpisode.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public void speedChange(float speed) {
        changeSpeed(speed);
    }
}
