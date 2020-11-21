package com.thomas.video.ui.presenter;

import com.alibaba.fastjson.JSONObject;
import com.thomas.base.mvp.BaseMvpPresenter;
import com.thomas.video.data.OKData;
import com.thomas.video.net.RetrofitCallback;
import com.thomas.video.ui.contract.TopicContract;
import com.thomas.video.ui.model.TopicModel;

public class TopicPresenter extends BaseMvpPresenter<TopicContract.Model, TopicContract.View>
        implements TopicContract.Presenter {
    @Override
    protected TopicContract.Model createModel() {
        return new TopicModel();
    }

    @Override
    public void getResult(int pageNo, String typeId, String wd) {
        getModel().getResult(pageNo, typeId, wd, new RetrofitCallback() {
            @Override
            protected void onSuccess(String succeed) {
                if (isViewAttached()) {
                    String result = succeed.replace("$", "@");
                    OKData data = JSONObject.parseObject(result, OKData.class);

                    if (data != null) {
                        getView().hasMoreData(data.getPage() < data.getPagecount());
                        if (data.getList() != null && data.getList().size() > 0) {
                            getView().getDataSuccess(data.getList());
                        } else {
                            getView().getDataEmpty();
                        }
                    } else {
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
