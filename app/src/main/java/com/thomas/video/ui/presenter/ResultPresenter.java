package com.thomas.video.ui.presenter;

import com.alibaba.fastjson.JSONObject;
import com.thomas.base.mvp.BaseMvpPresenter;
import com.thomas.video.data.OKData;
import com.thomas.video.net.RetrofitCallback;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.model.ResultModel;

public class ResultPresenter extends BaseMvpPresenter<ResultContract.Model, ResultContract.View> implements ResultContract.Presenter {
    @Override
    protected ResultContract.Model createModel() {
        return new ResultModel();
    }

    @Override
    public void getResult(int pageNo, String type, String wd) {
        getModel().getResult(pageNo, type, wd, new RetrofitCallback() {
            @Override
            protected void onSuccess(String succeed) {
                if (isViewAttached()) {
                    String result = succeed.replace("$", "@");
                    OKData data = JSONObject.parseObject(result, OKData.class);
                    if (data!=null){
                        getView().hasMoreData(data.getPage() < data.getPagecount());
                        if (data.getList() != null && data.getList().size() > 0) {
                            getView().getDataSuccess(data.getList());
                        } else {
                            getView().getDataEmpty();
                        }
                    }else {
                        getView().onFailed(0, "数据异常，请稍后重试");
                    }

                }


            }

            @Override
            protected void onFailed(String failed) {
                if (isViewAttached()) {
                    getView().onFailed(0, failed);
                }
            }
        });
    }
}
