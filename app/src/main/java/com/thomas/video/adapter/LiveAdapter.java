package com.thomas.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.data.LiveData;

import org.jetbrains.annotations.NotNull;

public class LiveAdapter extends BaseQuickAdapter<LiveData, BaseViewHolder> {
    public LiveAdapter() {
        super(R.layout.item_live);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LiveData liveData) {
        baseViewHolder.setText(R.id.tv_content, liveData.getTitle());
    }
}
