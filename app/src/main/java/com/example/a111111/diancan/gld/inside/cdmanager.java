package com.example.a111111.diancan.gld.inside;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.MainActivity;
import com.example.a111111.diancan.R;
import com.example.a111111.diancan.gld.manager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Integer.valueOf;

public class cdmanager extends AppCompatActivity {
    public class erro {
        private int errno;
        private int id;
        erro(int errno,int id){
            this.errno = errno;
            this.id = id;
        }

    }
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 0:
                    Toast.makeText(cdmanager.this, "修改成功", Toast.LENGTH_SHORT).show();

                    listView.setAdapter(new cdAdapter(cdmanager.this, (LinkedList<cdlist>) mData,cdmanager.this));
                    break;
                case 1:
                    Toast.makeText(cdmanager.this, "修改失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    static public int wait=0;
    static public Bitmap bitlog;
    List<cdlist> mData;
    String file_str = Environment.getExternalStorageDirectory().getPath();
    File mars_file = new File(file_str + "/my_camera");
    File file_go= new File(file_str + "/my_camera/file.jpg");
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(requestCode+":"+resultCode, "onActivityResult: ");
        if (requestCode == 0x1 ) {
            Toast.makeText(cdmanager.this, "OK", Toast.LENGTH_SHORT).show();
/* 使用BitmapFactory.Options类防止OOM(Out Of Memory)的问题；
创建一个BitmapFactory.Options类用来处理bitmap；*/
            BitmapFactory.Options myoptions = new BitmapFactory.Options();
/* 设置Options对象inJustDecodeBounds的属性为true，用于在BitmapFactory的
decodeFile(String path, Options opt)后获取图片的高和宽；
而且设置了他的属性值为true后使用BitmapFactory的decodeFile()方法无法返回一张
图片的bitmap对象，仅仅是把图片的高和宽信息给Options对象；
*/

            myoptions.inJustDecodeBounds = true;
            Bitmap bm = BitmapFactory.decodeFile(file_go.getAbsolutePath());
            BitmapFactory.decodeFile(file_go.getAbsolutePath(), myoptions);
//根据在图片的宽和高，得到图片在不变形的情况指定大小下的缩略图,设置宽为222；
            int height = myoptions.outHeight * 512 / myoptions.outWidth;
            myoptions.outWidth = 512;
            myoptions.outHeight = height ;
//在重新设置玩图片显示的高和宽后记住要修改，Options对象inJustDecodeBounds的属性为false;
//不然无法显示图片;
            myoptions.inJustDecodeBounds = false;
//还没完这里才刚开始,要节约内存还需要几个属性，下面是最关键的一个；
            myoptions.inSampleSize = myoptions.outWidth / 222;
//还可以设置其他几个属性用于缩小内存；

            myoptions.inPurgeable = true;
            myoptions.inInputShareable = true;
            myoptions.inPreferredConfig = Bitmap.Config.ARGB_4444;// 默认是Bitmap.Config.ARGB_8888
//成功了，下面就显示图片咯；
            Bitmap bitlogg = BitmapFactory.decodeFile(file_go.getAbsolutePath(), myoptions);
            Matrix m = new Matrix();
            m.setRotate(90, (float) bitlogg.getWidth()/2, (float) bitlogg.getHeight()/2 );
            m.postScale((float) 0.5, (float) 0.5);

            try {
                bitlog = Bitmap.createBitmap(bitlogg, 0, 0, bitlogg.getWidth(), bitlogg.getHeight(), m, true);
            }catch (OutOfMemoryError ex) {
            }
            wait=1;
        } else {
            System.out.println("不显示图片");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
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
                case 5:
                    Toast.makeText(cdmanager.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(cdmanager.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1111:

                    mData=getData();
                    listView.setAdapter(new cdAdapter(cdmanager.this, (LinkedList<cdlist>) mData,cdmanager.this));
                    break;


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdmanager);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        listView = (ListView) findViewById(R.id.listview);
        TextView add=(TextView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = cdmanager.this.getResources();
                Bitmap bmp= BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
                final CDialog2.Builder builder = new CDialog2.Builder(cdmanager.this);
                builder.setTitle("新建菜单项");
                builder.setpp(bmp);
                builder.setname("");
                builder.setdesc("");
                builder.setprice("");
                builder.setPositiveButton("提交菜单", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread()
                        {
                            @Override
                            public void run() {
                                try {
                                    FormBody.Builder pa = new FormBody.Builder();
                                    pa.add("status", "1");
                                    pa.add("name", builder.getname());
                                    pa.add("desc", builder.getdesc());
                                    pa.add("price", builder.getprice());
                                    pa.add("img", builder.getbit());

                                    tt = post(pa, "cdata.php");
                                    Gson gson = new Gson();
                                    int er = gson.fromJson(tt, cdmanager.erro.class).errno;
                                    mData.add(new cdlist(gson.fromJson(tt, cdmanager.erro.class).id+"", builder.getname() , builder.getprice(),builder.getdesc(),builder.getbit()));
                                    if (er == 0) hand.sendEmptyMessage(0);
                                    else if (er == 1) hand.sendEmptyMessage(1);
                                }
                                catch (Exception e)
                                {
                                    hand.sendEmptyMessage(2);
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
