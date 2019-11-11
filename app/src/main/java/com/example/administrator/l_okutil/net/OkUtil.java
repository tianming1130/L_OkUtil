package com.example.administrator.l_okutil.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.l_okutil.bean.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/11/11.
 */

public class OkUtil {
    public static  OkUtil okUtil;
    private Handler mHandler;
    private MyCallBack mMyCallBack;
    private OkUtil(){
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String strData=msg.getData().getString("data");

                Gson gson=new Gson();
                HttpResult<List<Menu>> httpResult=gson.fromJson(strData,new TypeToken<HttpResult<List<Menu>>>() {}.getType());
                mMyCallBack.onResponse(httpResult.getData());
            }
        };
    };
    public static OkUtil getInstance(){
        if (okUtil==null){
            okUtil=new OkUtil();
        }
        return okUtil;
    }
    public void getData(MyCallBack callback){
         this.mMyCallBack=callback;

        OkHttpClient okHttpClient=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://10.0.2.2/getdata.php")
                .get()
                .build();


        Call call=okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data=response.body().string();
                Message msg=mHandler.obtainMessage();
                Bundle bundle=new Bundle();
                bundle.putString("data",data);
                msg.setData(bundle);

                mHandler.sendMessage(msg);
            }
        });
    }
}
