package com.thomas.video.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;

import org.jetbrains.annotations.NotNull;

public class PersonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PersonAdapter() {
        super(R.layout.item_person);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_content, s);
    }
}
