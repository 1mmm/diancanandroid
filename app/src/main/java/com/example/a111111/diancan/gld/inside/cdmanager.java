package com.example.a111111.diancan.gld.inside;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

import static java.lang.Integer.valueOf;

public class cdmanager extends AppCompatActivity {
    String tt="";
    String ttt="";
    public JSONArray qqq;
    ListView listView;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    final Handler handed = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    Toast.makeText(cdmanager.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(cdmanager.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1111:
                    listView = (ListView) findViewById(R.id.listview);
                    List<cdlist> mData=getData();
                    listView.setAdapter(new cdAdapter(cdmanager.this, (LinkedList<cdlist>) mData,cdmanager.this));
                    break;


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdmanager);
        new Thread() {
            public void run() {
                try {
                    FormBody.Builder pa = new FormBody.Builder();
                    pa.add("status", "4");
                    tt = post(pa, "cdata.php");
                    JSONObject result = new JSONObject(tt);
                    qqq = result.getJSONArray("data");
                    handed.sendEmptyMessage(1111);
                } catch (Exception e) {
                }
            }
        }.start();

    }
    public List<cdlist> getData(){
        List<cdlist> list=new LinkedList<cdlist>();
        try {
            for (int i = 0; i < qqq.length(); i++) {
                JSONObject jo1 = qqq.getJSONObject(i);
                list.add(new cdlist(jo1.getString("id"), jo1.getString("name") , jo1.getString("price"),jo1.getString("desc"),jo1.getString("img")));
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
