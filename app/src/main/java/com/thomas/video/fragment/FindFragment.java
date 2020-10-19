package com.thomas.video.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.thomas.core.ActivityUtils;
import com.thomas.core.KeyboardUtils;
import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.core.AbstractLazyFragment;
import com.thomas.video.ui.ResultActivity;

import butterknife.BindView;

public class FindFragment extends AbstractLazyFragment {

    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R.id.card_search)
    MaterialCardView cardSearch;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.rv_new)
    RecyclerView rvNew;

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    private FindFragment() {
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

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
    }

    private void toResult() {
        String searchText = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            ToastUtils.showLong("请先输入要搜索内容");
        } else {
            KeyboardUtils.hideSoftInput(mActivity);
            etSearch.getText().clear();
            //保存搜索记录
            Bundle bundle = new Bundle();
            bundle.putString("content", searchText);
            ActivityUtils.startActivity(bundle, ResultActivity.class);
        }
    }
}
