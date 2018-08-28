package com.example.a111111.diancan.gld.inside;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.LinkedList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class gkAdapter extends BaseAdapter {
    public LinkedList<gklist> data;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    String tt="";
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity Ac;

    public gkAdapter(Context context, LinkedList<gklist> data, Activity Ac){
        this.context=context;
        this.data=data;
        this.Ac=Ac;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public final class Zujian{
        public TextView nichen;
        public TextView code;
        public TextView firstname;
        public TextView level;
        public Button xg,sc;

    }
    public class erro {
        private int errno;
        erro(int errno){
            this.errno = errno;
        }

    }
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 0:
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
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
        gkAdapter.Zujian zujian=null;
        zujian=new gkAdapter.Zujian();
        //获得组件，实例化组件
        convertView=layoutInflater.inflate(R.layout.gklist, parent,false);
        zujian.firstname=(TextView)convertView.findViewById(R.id.firstname);
        zujian.nichen=(TextView)convertView.findViewById(R.id.nichen);
        zujian.code=(TextView)convertView.findViewById(R.id.code);
        zujian.level=(TextView)convertView.findViewById(R.id.level);
        zujian.xg=(Button) convertView.findViewById(R.id.xg);
        zujian.sc=(Button) convertView.findViewById(R.id.sc);
        //绑定数据
        String h=data.get(position).getFirstname()+"";
        zujian.firstname.setText(h);
        h=data.get(position).getLevel()+"";
        zujian.level.setText(h);
        h=data.get(position).getCode()+"";
        zujian.code.setText(h);
        h=data.get(position).getNichen()+"";
        zujian.nichen.setText(h);
        zujian.xg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Ac);
                builder.setTitle("请选择权限等级");
                final String[] levelnum = {"1", "2", "3", "4", "5"};
                final int[] choose = new int[1];
                builder.setSingleChoiceItems(levelnum,0 , new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        choose[0] =which;
                    }
                });
                builder.setPositiveButton("确定并提交", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, final int which) {
                        new Thread()
                        {
                            @Override
                            public void run() {
                                try {
                                    FormBody.Builder pa = new FormBody.Builder();
                                    pa.add("type", "4");
                                    pa.add("level", levelnum[choose[0]]);
                                    pa.add("nichen",data.get(position).getNichen() );
                                    tt = post(pa, "registor.php");
                                    Gson gson = new Gson();
                                    int er = gson.fromJson(tt, gkAdapter.erro.class).errno;
                                    if (er == 0) hand.sendEmptyMessage(0);
                                    else if (er == 1) hand.sendEmptyMessage(1);
                                }
                                catch (Exception e)
                                {
                                    hand.sendEmptyMessage(4);
                                }
                            }


                        }.start();
                        data.get(position).setLevel(levelnum[choose[0]]);
                        notifyDataSetChanged();
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
        zujian.sc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String ni=data.get(position).getNichen();
                new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder pa = new FormBody.Builder();
                            pa.add("type", "5");
                            pa.add("nichen", ni);
                            tt = post(pa, "registor.php");
                            Gson gson = new Gson();
                            int er = gson.fromJson(tt, gkAdapter.erro.class).errno;
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
    String post(FormBody.Builder pa,String UR) throws Exception {
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
