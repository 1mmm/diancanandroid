package com.example.a111111.diancan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import static java.lang.Integer.valueOf;

public class welcome extends Activity {
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    String tt="",ttt="",aa="";
    StringBuffer sb = new StringBuffer();
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 0:
                    Toast.makeText(welcome.this, "读取用户资料成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(welcome.this, "读取用户资料失败，返回登陆界面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(welcome.this, MainActivity.class);
                    startActivity(intent);
                    welcome.this.finish();
                    break;
                case 33:
                    Log.d( "handleMessage: ",tt);
                    Log.d( "handleMessage: ",ttt);
                    break;

            }
        }
    };
    private final long SPLASH_LENGTH = 1000;
    Handler handler = new Handler();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(welcome.this, MainActivity.class);
                startActivity(intent);
                welcome.this.finish();
            }
        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity
    }
    String post(FormBody.Builder pa, String UR) throws Exception {
        //post方法接收一个RequestBody对象
        //create方法第一个参数都是MediaType类型，create方法的第二个参数可以是String、File、byte[]或okio.ByteString
        Request request = new Request.Builder()
                .url(base_url+UR)
                .post(pa.build())
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        else {
            return response.body().string();

        }
    }
}
