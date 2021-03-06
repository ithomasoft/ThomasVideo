package com.thomas.video.ui.contract;

import com.thomas.base.mvp.IBaseMvpModel;
import com.thomas.base.mvp.IBaseMvpView;
import com.thomas.video.data.VideoData;
import com.thomas.video.net.RetrofitCallback;

import java.util.List;

public interface ResultContract {
    interface Model extends IBaseMvpModel {
        void getResult(int pageNo,String type, String wd, RetrofitCallback callback);
    }

    interface View extends IBaseMvpView {
        void getDataSuccess(List<VideoData> succeed);

        void getDataEmpty();

        void hasMoreData(boolean hasMoreData);
    }

    interface Presenter {
        void getResult(int pageNo, String type, String wd);
    }
}
