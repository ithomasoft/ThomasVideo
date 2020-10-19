package com.thomas.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.core.ColorUtils;
import com.thomas.video.R;
import com.thomas.video.data.EpisodeData;

import org.jetbrains.annotations.NotNull;

public class VideoEpisodeAdapter extends BaseQuickAdapter<EpisodeData, BaseViewHolder> {

    public VideoEpisodeAdapter() {
        super(R.layout.item_video_episode);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, EpisodeData episodeData) {
        baseViewHolder.setText(R.id.item_episodeNum,episodeData.getVideoName());
        baseViewHolder.setTextColor(R.id.item_episodeNum,episodeData.isPlay()? ColorUtils.getColor(R.color.colorPrimary):ColorUtils.getColor(android.R.color.white));
        baseViewHolder.setBackgroundResource(R.id.item_episodeNum,episodeData.isPlay()?R.drawable.bg_video_episodes_check:R.drawable.bg_video_episodes_uncheck);
    }

}
