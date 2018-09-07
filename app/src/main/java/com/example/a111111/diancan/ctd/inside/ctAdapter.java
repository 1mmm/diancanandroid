package com.example.a111111.diancan.ctd.inside;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ctAdapter extends BaseAdapter {
    public LinkedList<dininglist> data = new LinkedList<dininglist>();
    public LinkedList<dininglist> tmplist = new LinkedList<dininglist>();
    public LinkedList<dininglist> newdata = new LinkedList<dininglist>();
    public String base_url = "http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    String tt = "";
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity Ac;
    String file_str = Environment.getExternalStorageDirectory().getPath();
    File mars_file = new File(file_str + "/my_camera");
    File file_go;

    public ctAdapter(Context context, LinkedList<dininglist> data, Activity Ac) {
        this.context = context;
        this.data = data;
        this.Ac = Ac;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Zujian {
        public TextView tableid;
        public TextView userid;
        public TextView dishid;
        public TextView dishnum;
        public TextView waittime;
        public Button finish;

    }

    public class erro {
        private int errno;

        erro(int errno) {
            this.errno = errno;
        }

    }

    final Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 2:
                    Toast.makeText(context, "订单完成", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "订单失败", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1111:
                    Toast.makeText(context, "1111", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ctAdapter.Zujian zujian = null;
        zujian = new ctAdapter.Zujian();
        //获得组件，实例化组件
        convertView = layoutInflater.inflate(R.layout.ctlist, parent, false);
        zujian.tableid = (TextView) convertView.findViewById(R.id.tableid);
        zujian.userid = (TextView) convertView.findViewById(R.id.userid);
        zujian.dishnum = (TextView) convertView.findViewById(R.id.dishnum);
        zujian.dishid = (TextView) convertView.findViewById(R.id.dishid);
        zujian.waittime = (TextView) convertView.findViewById(R.id.waittime);
        zujian.finish = (Button) convertView.findViewById(R.id.finish);


        String h = data.get(position).getId() + "";
        zujian.tableid.setText(h);
        h = data.get(position).getUid() + "";
        zujian.userid.setText(h);
        h = data.get(position).getMenu() + "";
        zujian.dishid.setText(h);
        h = data.get(position).getNum() + "";
        zujian.dishnum.setText(h);
        h = data.get(position).getTime();
        zujian.waittime.setText(h);


        zujian.finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder pa = new FormBody.Builder();
                            pa.add("status", "3");
                            pa.add("id", data.get(position).getId());
                            tt = post(pa, "data.php");
                            Gson gson = new Gson();
                            int er = gson.fromJson(tt, ctAdapter.erro.class).errno;
                            if (er == 0) hand.sendEmptyMessage(2);
                            else if (er == 1) hand.sendEmptyMessage(3);
                        }
                        catch (Exception e)
                        {
                            hand.sendEmptyMessage(4);
                        }
                    }


                }.start();
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
        String post (FormBody.Builder pa, String UR) throws Exception {
            //post方法接收一个RequestBody对象
            //create方法第一个参数都是MediaType类型，create方法的第二个参数可以是String、File、byte[]或okio.ByteString

            Request request = new Request.Builder()
                    .url(base_url + UR)
                    .post(pa.build())
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            else {
                return response.body().string();

            }
        }
    private static  String dqsj() {  //此方法用于获得当前系统时间（格式类型2007-11-6 15:10:58）
        Date date = new Date();  //实例化日期类型
        String today = date.toLocaleString(); //获取当前时间
        return today;  //返回当前时间
    }
    public LinkedList<dininglist> mergeTwoSortedLinkedList(LinkedList<dininglist> list0, LinkedList<dininglist> list1) {
        ListIterator<dininglist> it0 = list0.listIterator();
        ListIterator<dininglist> it1 = list1.listIterator();
        LinkedList<dininglist> result = new LinkedList<dininglist>();
        while(it0.hasNext()) {
            if (it0.hasNext()) {
                dininglist val0 = it0.next();
                result.add(val0);
            }
        }
        while (it1.hasNext()){
            if(it1.hasNext()){
                dininglist val = it1.next();
                result.add(val);
            }
            else if(!it1.hasNext())
                return result;
        }
        return result;
    }

}
