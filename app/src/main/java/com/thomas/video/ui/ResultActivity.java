package com.thomas.video.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.thomas.core.ActivityUtils;
import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.ResultAdapter;
import com.thomas.video.core.AbstractMvpActivity;
import com.thomas.video.data.OKData;
import com.thomas.video.data.VideoData;
import com.thomas.video.helper.LoadingHelper;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.presenter.ResultPresenter;
import com.thomas.video.widget.EmptyView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.List;

import butterknife.BindView;

public class ResultActivity extends AbstractMvpActivity<ResultPresenter> implements ResultContract.View {
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private String wd;
    private String type;
    private int pageNo = 1;
    private ResultAdapter mAdapter;

    @Override
    protected ResultPresenter createPresenter() {
        return new ResultPresenter();
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        wd = bundle.getString("content");
        type = bundle.getString("type");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_result;
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
        if (emptyView == null) {
            emptyView = new EmptyView(mActivity);
        }
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                presenter.getResult(pageNo, type, wd);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                presenter.getResult(pageNo, type, wd);
            }
        });
        mAdapter = new ResultAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", mAdapter.getData().get(position));
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
    }

    @Override
    public void doBusiness() {
        LoadingHelper.showLoading();
        presenter.getResult(pageNo, type, wd);
    }


    @Override
    public void getDataSuccess(List<VideoData> succeed) {
        mAdapter.setUseEmpty(false);
        if (pageNo == 1) {
            mAdapter.getData().clear();
        }
        mAdapter.addData(succeed);
    }

    @Override
    public void getDataEmpty() {
        mAdapter.setUseEmpty(true);
        mAdapter.setList(null);
        emptyView.setTips("暂未收集到资源");
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public void hasMoreData(boolean hasMoreData) {
        LoadingHelper.hideLoading();
        smartRefreshLayout.finishLoadMore(true);
        smartRefreshLayout.finishRefresh(true);
        smartRefreshLayout.setEnableLoadMore(hasMoreData);
    }

    @Override
    public void onFailed(Object tag, String failed) {
        LoadingHelper.hideLoading();
        smartRefreshLayout.finishLoadMore(false);
        smartRefreshLayout.finishRefresh(false);
        if (pageNo == 1) {
            mAdapter.setUseEmpty(true);
            emptyView.setTips(failed);
            mAdapter.setEmptyView(emptyView);
        } else {
            ToastUtils.showShort(failed);
        }
    }
}
