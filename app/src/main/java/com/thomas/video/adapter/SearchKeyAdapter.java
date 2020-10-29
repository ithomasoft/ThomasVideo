package com.thomas.video.adapter;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.data.SearchHistoryData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchKeyAdapter extends BaseQuickAdapter<SearchHistoryData, BaseViewHolder> {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public SearchKeyAdapter() {
        super(R.layout.item_search_key);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SearchHistoryData data) {
        baseViewHolder.setText(R.id.tv_content, data.getKey());
        setStatusChange(baseViewHolder, data, false);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, SearchHistoryData item, @NotNull List<?> payloads) {

        for (Object payload : payloads) {
            if (payload instanceof Integer && (int) payload == EXPAND_COLLAPSE_PAYLOAD) {
                // 增量刷新，使用动画变化状态
                setStatusChange(holder, item, true);
            }
        }
    }

    private void setStatusChange(BaseViewHolder holder, SearchHistoryData item, boolean isAnimate) {
        AppCompatImageView ivDelete = holder.getView(R.id.iv_delete);
        if (item.isShowDelete()) {
            if (isAnimate) {
                ViewCompat.animate(ivDelete).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .alpha(1)
                        .start();
                ivDelete.setVisibility(View.VISIBLE);
            } else {
                ivDelete.setVisibility(View.VISIBLE);
            }
        } else {
            if (isAnimate) {
                ViewCompat.animate(ivDelete).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .alpha(0)
                        .start();
                ivDelete.setVisibility(View.INVISIBLE);
            } else {
                ivDelete.setVisibility(View.INVISIBLE);
            }
        }
    }
}
