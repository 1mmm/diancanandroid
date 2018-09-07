package com.example.a111111.diancan.gkd.inside;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;
import com.example.a111111.diancan.gkd.manager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.a111111.diancan.login.fn;
import static com.example.a111111.diancan.login.ln;
public class cdmanager extends AppCompatActivity {
    static public int[] num=new int[1000];
    static public int len=0;
    public class erro {
        private int errno;
        private int id;
        erro(int errno,int id){
            this.errno = errno;
            this.id = id;
        }

    }
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
                    mData=getData();
                    listView.setAdapter(new cdAdapter(cdmanager.this, (LinkedList<cdlist>) mData, cdmanager.this));
                    break;


            }
        }
    };
    private adder adv;
    String tt="";
    List<cdlist> mData;
    String ttt="";
    public JSONArray qqq;
    ListView listView;
    Button qr;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camanager);
        LayoutInflater inflater = LayoutInflater.from(this);
        listView = (ListView) findViewById(R.id.listview);
        qr = (Button) findViewById(R.id.qr);
        View view = inflater.inflate( R.layout.blank_foot, null);
        listView.addFooterView( view);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h="";
                int zj=0;
                for (int i=0;i<len;i++)
                {
                    if (num[i]>0)
                    {
                        String s="";
                        s=mData.get(i).getName()+"  ￥"+mData.get(i).getPrice()+" X "+num[i]+"份\n";
                        zj=zj+Integer.valueOf(mData.get(i).getPrice())*num[i];
                        h=h+s;
                    }
                }
                final CDialog.Builder builder = new CDialog.Builder(cdmanager.this);
                builder.setTitle("确认下单？");
                builder.setyd(h);
                builder.setprice(zj+"元");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            public void run() {
                                try {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    for (int i=0;i<len;i++)
                                    {
                                        if (num[i]>0)
                                        {
                                            FormBody.Builder pa = new FormBody.Builder();
                                            pa.add("status", "1");
                                            pa.add("uid", ln+" "+fn);
                                            pa.add("menu", mData.get(i).getName());
                                            pa.add("num", ""+num[i]);
                                            pa.add("tim", df.format(new Date()));
                                            tt = post(pa, "data.php");
                                        }
                                    }
                                    handed.sendEmptyMessage(5);

                                } catch (Exception e) {
                                    handed.sendEmptyMessage(6);
                                }
                            }
                        }.start();
                        dialog.dismiss();
                    }

                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                builder.create().show();
            }
        });
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
            len=qqq.length();
            for (int i = 0; i < qqq.length(); i++) {
                num[i]=0;
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
}

