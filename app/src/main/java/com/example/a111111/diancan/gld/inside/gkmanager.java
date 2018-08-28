package com.example.a111111.diancan.gld.inside;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class gkmanager extends AppCompatActivity {
    String tt="";
    String ttt="";
    List<gklist> mData;
    public JSONArray qqq;
    ListView listView;
    TextView add;
    public String base_url="http://39.107.93.96/";
    final Handler handed = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    Toast.makeText(gkmanager.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(gkmanager.this, "请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1111:

                    mData=getData();
                    listView.setAdapter(new gkAdapter(gkmanager.this, (LinkedList<gklist>) mData,gkmanager.this));
                    break;


            }
        }
    };
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkmanager);
        listView = (ListView) findViewById(R.id.listview);
        new Thread() {
            public void run() {
                try {
                    FormBody.Builder pa = new FormBody.Builder();
                    pa.add("type", "3");
                    tt = post(pa, "registor.php");
                    JSONObject result = new JSONObject(tt);
                    qqq = result.getJSONArray("data");
                    handed.sendEmptyMessage(1111);
                } catch (Exception e) {
                    handed.sendEmptyMessage(6);
                }
            }
        }.start();
    }

    public List<gklist> getData(){
        List<gklist> list=new LinkedList<gklist>();
        try {
            for (int i = 0; i < qqq.length(); i++) {
                JSONObject jo1 = qqq.getJSONObject(i);
                list.add(new gklist(jo1.getString("id"), jo1.getString("lastname") , jo1.getString("firstname"),jo1.getString("code"),jo1.getString("nichen"),jo1.getString("level"),jo1.getString("age")));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
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
    private void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
