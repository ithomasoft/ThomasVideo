package com.thomas.video.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.card.MaterialCardView;
import com.thomas.core.ActivityUtils;
import com.thomas.core.KeyboardUtils;
import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.SearchKeyAdapter;
import com.thomas.video.core.AbstractLazyFragment;
import com.thomas.video.data.SearchHistoryData;
import com.thomas.video.helper.DataHelper;
import com.thomas.video.ui.ResultActivity;

import butterknife.BindView;

public class FindFragment extends AbstractLazyFragment {

    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R.id.card_search)
    MaterialCardView cardSearch;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.stv_title)
    SuperTextView stvTitle;

    private SearchKeyAdapter searchKeyAdapter;

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    private FindFragment() {
    }

    @Override
    protected void onFirstUserVisible() {
        searchKeyAdapter.setNewInstance(DataHelper.getSearchKeyList());
    }

    @Override
    protected void onUserVisible() {
        searchKeyAdapter.setNewInstance(DataHelper.getSearchKeyList());
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toResult();
                return true;
            } else {
                return false;
            }

        });
        stvTitle.getRightTextView().setVisibility(View.INVISIBLE);
        applyThomasClickScaleListener(stvTitle.getRightTextView());
        searchKeyAdapter = new SearchKeyAdapter();
        rvHistory.setLayoutManager(new FlexboxLayoutManager(mActivity, FlexDirection.ROW));
        rvHistory.setAdapter(searchKeyAdapter);
        searchKeyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SearchHistoryData data = searchKeyAdapter.getItem(position);
                if (data.isShowDelete()) {
                    DataHelper.deleteSearchKey(data.getKey());
                    searchKeyAdapter.remove(data);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", data.getKey());
                    bundle.putString("type", data.getType());
                    ActivityUtils.startActivity(bundle, ResultActivity.class);
                }

            }
        });
        searchKeyAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                boolean showDelete = searchKeyAdapter.getItem(position).isShowDelete();
                updateStatus(showDelete);
                return true;
            }
        });
    }

    /**
     * 更新状态
     *
     * @param showDelete
     */
    private void updateStatus(boolean showDelete) {
        if (showDelete) {
            stvTitle.getRightTextView().setVisibility(View.GONE);
        } else {
            stvTitle.getRightTextView().setVisibility(View.VISIBLE);
        }
        for (SearchHistoryData data : searchKeyAdapter.getData()) {
            data.setShowDelete(!showDelete);
        }
        searchKeyAdapter.notifyItemRangeChanged(0, searchKeyAdapter.getItemCount(), SearchKeyAdapter.EXPAND_COLLAPSE_PAYLOAD);

    }

    private void toResult() {
        String searchText = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            ToastUtils.showLong("请先输入要搜索内容");
        } else {
            KeyboardUtils.hideSoftInput(mActivity);
            etSearch.getText().clear();
            //保存搜索记录
            DataHelper.saveSearchKey(searchText, "");
            Bundle bundle = new Bundle();
            bundle.putString("content", searchText);
            ActivityUtils.startActivity(bundle, ResultActivity.class);
        }
    }

    @Override
    public void onThomasClick(@NonNull View view) {
        if (view == stvTitle.getRightTextView()) {
            DataHelper.deleteAllSearchKey();
            searchKeyAdapter.getData().clear();
            searchKeyAdapter.notifyDataSetChanged();
            stvTitle.getRightTextView().setVisibility(View.GONE);
        }
    }
}
