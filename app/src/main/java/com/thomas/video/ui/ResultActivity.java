package com.thomas.video.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.thomas.core.ActivityUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.ResultAdapter;
import com.thomas.video.core.AbstractMvpActivity;
import com.thomas.video.data.OKData;
import com.thomas.video.helper.LoadingHelper;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.presenter.ResultPresenter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AbstractMvpActivity<ResultPresenter> implements ResultContract.View {
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private String wd;
    private int pageNo = 1;
    private ResultAdapter mAdapter;

    @Override
    protected ResultPresenter createPresenter() {
        return new ResultPresenter();
    }

    @Override
    public void onFailed(Object tag, String failed) {
        LoadingHelper.hideLoading();
        smartRefreshLayout.finishLoadMore(false);
        smartRefreshLayout.finishRefresh(false);
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        wd = bundle.getString("content");
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
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                presenter.getResult(pageNo, wd);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                presenter.getResult(pageNo, wd);
            }
        });
        mAdapter = new ResultAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mAdapter.getData().get(position));
                ActivityUtils.startActivity(bundle, DetailActivity.class);
            }
        });
    }

    @Override
    public void doBusiness() {
        LoadingHelper.showLoading();
        presenter.getResult(pageNo, wd);
    }


    @Override
    public void getDataSuccess(List<OKData.ListBean> succeed) {
        if (pageNo == 1) {
            mAdapter.getData().clear();
        }
        mAdapter.addData(succeed);
    }

    @Override
    public void getDataEmpty() {
    }

    @Override
    public void hasMoreData(boolean hasMoreData) {
        LoadingHelper.hideLoading();
        smartRefreshLayout.finishLoadMore(true);
        smartRefreshLayout.finishRefresh(true);
        smartRefreshLayout.setEnableLoadMore(hasMoreData);
    }
}
