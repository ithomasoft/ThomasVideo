package com.thomas.video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OKService {
    String BASE_URL = "https://api.okzy.tv/api.php/provide/vod/at/json/";

    @GET("?ac=list")
    Call<String> getList();

    @GET("?ac=detail")
    Call<String> getDetail(@Query("t") String typeId,
                           @Query("pg") int pageNo,
                           @Query("wd") String keyWords,
                           @Query("h") String hours);

}
