package com.thomas.video.ui.model;

import com.thomas.video.OKService;
import com.thomas.video.net.RetrofitCallback;
import com.thomas.video.net.RetrofitFactory;
import com.thomas.video.ui.contract.TopicContract;

public class TopicModel implements TopicContract.Model {
    @Override
    public void getResult(int pageNo, String typeId, String wd, RetrofitCallback callback) {
        RetrofitFactory.createApi(OKService.class)
                .getDetail(typeId, pageNo, wd, null).enqueue(callback);
    }
}
