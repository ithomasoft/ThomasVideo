package com.thomas.video.adapter;

import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.data.VideoData;

import org.jetbrains.annotations.NotNull;

public class ResultAdapter extends BaseQuickAdapter<VideoData, BaseViewHolder> {
    public ResultAdapter() {
        super(R.layout.item_result);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, VideoData listBean) {
        baseViewHolder.setVisible(R.id.ll_type_record, listBean.getType_id() == 36 || listBean.getType_id() == 34);
        baseViewHolder.setGone(R.id.ll_type_video, listBean.getType_id() == 36 || listBean.getType_id() == 34);
        if (listBean.getType_id() == 36 || listBean.getType_id() == 34) {
            Glide.with(baseViewHolder.itemView).load(listBean.getVod_pic()).centerCrop()
                    .placeholder(R.drawable.ic_splash_logo_jrys)
                    .error(R.drawable.ic_splash_logo_jrys)
                    .into((AppCompatImageView) baseViewHolder.findView(R.id.iv_record_pic));
            baseViewHolder.setText(R.id.tv_record_title, listBean.getVod_name());
        } else {
            baseViewHolder.setText(R.id.tv_name, listBean.getVod_name());
            baseViewHolder.setGone(R.id.tv_remarks, TextUtils.isEmpty(listBean.getVod_remarks()));
            baseViewHolder.setText(R.id.tv_remarks, listBean.getVod_remarks());
            Glide.with(baseViewHolder.itemView).load(listBean.getVod_pic()).centerCrop()
                    .placeholder(R.drawable.load)
                    .error(R.drawable.load)
                    .into((AppCompatImageView) baseViewHolder.findView(R.id.iv_pic));
            baseViewHolder.setText(R.id.tv_guide, listBean.getVod_director());
            baseViewHolder.setText(R.id.tv_actor, listBean.getVod_actor());
            baseViewHolder.setText(R.id.tv_class, listBean.getType_name());
            baseViewHolder.setGone(R.id.ll_actor, TextUtils.isEmpty(listBean.getVod_actor()));
            baseViewHolder.setGone(R.id.ll_guide, TextUtils.isEmpty(listBean.getVod_director()));
            baseViewHolder.setText(R.id.tv_blurb, listBean.getVod_blurb());
            baseViewHolder.setVisible(R.id.ll_blurb, TextUtils.isEmpty(listBean.getVod_actor()) || TextUtils.isEmpty(listBean.getVod_director()));
        }

    }
}
