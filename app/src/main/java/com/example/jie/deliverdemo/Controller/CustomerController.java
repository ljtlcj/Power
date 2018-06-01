package com.example.jie.deliverdemo.Controller;

import android.content.Context;
import android.util.Log;

import com.example.jie.deliverdemo.Utils.InterfaceManger;
import com.example.jie.deliverdemo.Utils.RetrofitUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2018/5/20.
 */

public class CustomerController {
    private static Context context;

    public CustomerController(Context context) {
        this.context = context;
    }


    public static void login(Map<String, RequestBody> map, MultipartBody.Part parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().login(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError(String.valueOf(response.code()));
                    return;
                }
                try {
                    String body = response.body().string();
                    Log.e("onResponse:1234", body);
                    JSONObject jsonObject = new JSONObject(body);
                    int code = jsonObject.getInt("code");
                    Object object = body;
                    if (code == 1) {
                        listener.onSuccess("上传成功");
                    } else {
                        listener.onError("上传失败");
                    }
                } catch (Exception e) {
                    listener.onError(e.toString());
                    e.printStackTrace();
                }
                listener.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                Log.e("onFailure", t.toString());
                if (t.toString().contains("ConnectException")) {
                    listener.onError("网络异常");
                } else {
                    listener.onError("网络异常");
                }
                listener.onComplete();
            }
        });
    }
}
