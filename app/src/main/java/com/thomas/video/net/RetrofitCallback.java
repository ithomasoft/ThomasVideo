package com.thomas.video.net;

import android.text.TextUtils;

import com.thomas.core.StringUtils;
import com.thomas.video.R;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallback implements Callback<String> {

    protected abstract void onSuccess(String succeed);

    protected abstract void onFailed(String failed);


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.errorBody() != null) {
            try {
                onFailed(response.errorBody().string());
            } catch (IOException e) {
                onFailed(e.toString());
            }
        } else if (response.isSuccessful()) {
            onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        String message;
        if (t instanceof ConnectException) {
            message = StringUtils.getString(R.string.http_exception_network);
        } else if (t instanceof URISyntaxException) {
            message = StringUtils.getString(R.string.http_exception_url);
        } else if (t instanceof UnknownHostException) {
            message = StringUtils.getString(R.string.http_exception_host);
        } else if (t instanceof UnknownServiceException) {
            message = StringUtils.getString(R.string.http_exception_connect_timeout);
        } else if (t instanceof SocketException) {
            message = StringUtils.getString(R.string.http_exception_write);
        } else if (t instanceof SocketTimeoutException) {
            message = StringUtils.getString(R.string.http_exception_read_timeout);
        } else {
            message = StringUtils.getString(R.string.http_exception_unknow_error);
        }
        ResponseBody responseBody = ResponseBody.create(message, MediaType.parse("application/json; charset=UTF-8"));
        Response<String> response = Response.error(-1, responseBody);
        onResponse(call, response);
    }
}
