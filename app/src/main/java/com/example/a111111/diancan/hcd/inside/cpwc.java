package com.example.a111111.diancan.hcd.inside;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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

public class cpwc extends AppCompatActivity {
    public class erro {
        private int errno;
        private int id;
        erro(int errno,int id){
            this.errno = errno;
            this.id = id;
        }

    }
    static public int wait=0;
    static public Bitmap bitlog;
    List<dininglist> mData;

    String tt="";
    String ttt="";
    public JSONArray qqq;
    ListView listView;
    TextView add;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    final Handler handed = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1111:

                    mData=getData();
                    listView.setAdapter(new ctAdapter(cpwc.this, (LinkedList<dininglist>) mData,cpwc.this));
                    break;


            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctmanager);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        listView = (ListView) findViewById(R.id.listview);

        new Thread() {
            public void run() {
                try {
                    FormBody.Builder pa = new FormBody.Builder();
                    pa.add("status", "2");
                    tt = post(pa, "data.php");
                    JSONObject result = new JSONObject(tt);
                    qqq = result.getJSONArray("data");
                    handed.sendEmptyMessage(1111);
                } catch (Exception e) {
                }
            }
        }.start();

    }
    public List<dininglist> getData(){
        List<dininglist> list=new LinkedList<dininglist>();
        try {
            for (int i = 0; i < qqq.length(); i++) {
                JSONObject jo1 = qqq.getJSONObject(i);
                list.add(new dininglist(jo1.getString("id"), jo1.getString("uid") , jo1.getString("menu"),jo1.getString("time"),jo1.getString("num")));
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
