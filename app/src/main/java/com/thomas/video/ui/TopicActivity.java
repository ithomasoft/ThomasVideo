package com.thomas.video.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.thomas.core.ActivityUtils;
import com.thomas.core.KeyboardUtils;
import com.thomas.core.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.ResultAdapter;
import com.thomas.video.core.AbstractMvpActivity;
import com.thomas.video.data.CategoryData;
import com.thomas.video.data.OKData;
import com.thomas.video.helper.DataHelper;
import com.thomas.video.helper.LoadingHelper;
import com.thomas.video.ui.contract.TopicContract;
import com.thomas.video.ui.presenter.TopicPresenter;
import com.thomas.video.widget.EmptyView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicActivity extends AbstractMvpActivity<TopicPresenter> implements TopicContract.View {

    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tab_type)
    TabLayout tabType;
    private int pageNo = 1;
    private ResultAdapter mAdapter;

    private String type;
    private String title;
    private List<CategoryData> categoryList = new ArrayList<>();
    private String currentType;
    private String wd = "";

    @Override
    protected TopicPresenter createPresenter() {
        return new TopicPresenter();
    }


    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        type = bundle.getString("type");
        title = bundle.getString("title");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_topic;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        titleBar.getCenterSearchEditText().setHint("搜你想看的" + title + "(仅支持名称搜索)");
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    ActivityUtils.finishActivity(mActivity, true);
                    return;
                }
                if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT) {
                    if (TextUtils.isEmpty(extra)){
                        ToastUtils.showLong("请先输入要搜索内容");
                    }else {
                        KeyboardUtils.hideSoftInput(mActivity);
                        //保存搜索记录
                        DataHelper.saveSearchKey(extra, "");
                        wd = extra;
                        rvContent.scrollToPosition(0);
                        smartRefreshLayout.autoRefresh();
                    }

                    return;
                }

                if (action == CommonTitleBar.ACTION_SEARCH_DELETE) {
                    wd = "";
                    rvContent.scrollToPosition(0);
                    smartRefreshLayout.autoRefresh();
                    return;
                }
            }
        });
        if (emptyView == null) {
            emptyView = new EmptyView(mActivity);
        }
        configTab();
        tabType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentType = categoryList.get(tab.getPosition()).getTypeId();
                rvContent.scrollToPosition(0);
                smartRefreshLayout.autoRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                currentType = categoryList.get(tab.getPosition()).getTypeId();
                smartRefreshLayout.autoRefresh();
            }
        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                presenter.getResult(pageNo, currentType, wd);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                presenter.getResult(pageNo, currentType, wd);
            }
        });
        mAdapter = new ResultAdapter();
        if (TextUtils.equals(currentType, "34")) {
            rvContent.setLayoutManager(new GridLayoutManager(mActivity, 2));
        } else {
            rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        }
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

    private void configTab() {
        switch (type) {
            case "36"://解说
                categoryList.add(new CategoryData("36", "电影解说"));
                break;
            case "1"://电影
                categoryList.add(new CategoryData("6", "动作片"));
                categoryList.add(new CategoryData("7", "喜剧片"));
                categoryList.add(new CategoryData("8", "爱情片"));
                categoryList.add(new CategoryData("9", "科幻片"));
                categoryList.add(new CategoryData("10", "恐怖片"));
                categoryList.add(new CategoryData("11", "剧情片"));
                categoryList.add(new CategoryData("12", "战争片"));
                categoryList.add(new CategoryData("20", "纪录片"));
                categoryList.add(new CategoryData("21", "微电影"));
                categoryList.add(new CategoryData("37", "伦理片"));
                break;
            case "2"://电视剧
                categoryList.add(new CategoryData("13", "国产剧"));
                categoryList.add(new CategoryData("14", "香港剧"));
                categoryList.add(new CategoryData("15", "韩国剧"));
                categoryList.add(new CategoryData("16", "欧美剧"));
                categoryList.add(new CategoryData("22", "台湾剧"));
                categoryList.add(new CategoryData("23", "日本剧"));
                categoryList.add(new CategoryData("24", "海外剧"));
                categoryList.add(new CategoryData("36", "电影解说"));
                break;
            case "3"://综艺
                categoryList.add(new CategoryData("25", "内地综艺"));
                categoryList.add(new CategoryData("26", "港台综艺"));
                categoryList.add(new CategoryData("27", "日韩综艺"));
                categoryList.add(new CategoryData("28", "欧美综艺"));
                break;
            case "4"://动漫
                categoryList.add(new CategoryData("29", "国产动漫"));
                categoryList.add(new CategoryData("30", "日韩动漫"));
                categoryList.add(new CategoryData("31", "欧美动漫"));
                categoryList.add(new CategoryData("32", "港台动漫"));
                categoryList.add(new CategoryData("33", "海外动漫"));
                break;
            case "34"://福利
                categoryList.add(new CategoryData("34", "福利片"));
                break;
        }
        if (categoryList.size() > 1) {
            tabType.setVisibility(View.VISIBLE);
        } else {
            tabType.setVisibility(View.GONE);
        }
        if (categoryList.size() > 4) {
            tabType.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabType.setTabMode(TabLayout.MODE_FIXED);
        }
        for (int i = 0; i < categoryList.size(); i++) {
            tabType.addTab(tabType.newTab().setText(categoryList.get(i).getTypeName()));
        }

        currentType = categoryList.get(0).getTypeId();
    }

    @Override
    public void doBusiness() {
        smartRefreshLayout.autoRefresh();
        presenter.getResult(pageNo, currentType, "");
    }

    @Override
    public void getDataSuccess(List<OKData.ListBean> succeed) {
        mAdapter.setUseEmpty(false);
        if (pageNo == 1) {
            mAdapter.getData().clear();
            rvContent.scrollToPosition(0);
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
