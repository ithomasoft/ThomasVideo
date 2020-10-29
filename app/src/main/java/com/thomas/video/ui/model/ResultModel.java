package com.thomas.video.ui.model;

import com.thomas.video.OKService;
import com.thomas.video.net.RetrofitCallback;
import com.thomas.video.net.RetrofitFactory;
import com.thomas.video.ui.contract.ResultContract;

public class ResultModel implements ResultContract.Model {
    @Override
    public void getResult(int pageNo, String type, String wd, RetrofitCallback callback) {
        RetrofitFactory.createApi(OKService.class)
                .getDetail(type, pageNo, wd, null).enqueue(callback);
    }
}
