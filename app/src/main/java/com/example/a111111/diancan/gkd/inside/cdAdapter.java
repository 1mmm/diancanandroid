package com.example.a111111.diancan.gkd.inside;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;
import com.example.a111111.diancan.login;
import com.example.a111111.diancan.register;
import com.example.a111111.diancan.welcome;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.a111111.diancan.gkd.inside.cdmanager.num;

public class cdAdapter extends BaseAdapter {
    public LinkedList<cdlist> data;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    String tt="";
    public   int index=-1;
    public  myWatcher mWatcher;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity Ac;
    String file_str = Environment.getExternalStorageDirectory().getPath();
    File mars_file = new File(file_str + "/my_camera");
    File file_go;
    public cdAdapter(Context context, LinkedList<cdlist> data, Activity Ac){
        this.context=context;
        this.data=data;
        this.Ac=Ac;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public final class Zujian{
        public TextView name;
        public TextView price;
        public TextView desc;
        public adder add;
        public ImageView img;

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
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(Ac,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0X11);
            //判断是否需要 向用户解释，为什么要申请该权限
            ActivityCompat.shouldShowRequestPermissionRationale(Ac,
                    Manifest.permission.READ_CONTACTS);
            //InputMethodManager  manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(Ac,
                    new String[]{Manifest.permission.CAMERA}, 0X11);
            //判断是否需要 向用户解释，为什么要申请该权限
            ActivityCompat.shouldShowRequestPermissionRationale(Ac,
                    Manifest.permission.READ_CONTACTS);
            //InputMethodManager  manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        final Zujian zujian=new Zujian();

        //获得组件，实例化组件
        convertView=layoutInflater.inflate(R.layout.dclist, parent,false);
        zujian.name=(TextView)convertView.findViewById(R.id.name);
        zujian.price=(TextView)convertView.findViewById(R.id.price);
        zujian.desc=(TextView)convertView.findViewById(R.id.desc);
        zujian.add=(adder) convertView.findViewById(R.id.adv_main);
        zujian.img=(ImageView )convertView.findViewById(R.id.tp);
        //绑定数据




        zujian.add.setOnAddDelClickListener(new adder.OnAddDelClickListener() {
            @Override
            public void onAddClick(View v) {
                int origin = zujian.add.getNumber();
                origin++;
                zujian.add.setNumber(origin);
                num[position]=zujian.add.getNumber();;
            }

            @Override
            public void onDelClick(View v) {
                int origin = zujian.add.getNumber();
                origin--;
                zujian.add.setNumber(origin);
                num[position]=zujian.add.getNumber();;
            }
        });
        String h=data.get(position).getName()+"";
        zujian.name.setText(h);
        h="￥"+data.get(position).getPrice();
        zujian.price.setText(h);
        h=data.get(position).getDesc()+"";
        zujian.desc.setText(h);
        h=file_str + "/my_camera/file.png";
        file_go= new File(h);
        string2Image(data.get(position).getImg(),h);
        BitmapFactory.Options myoptions = new BitmapFactory.Options();
        myoptions.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(file_go.getAbsolutePath());
        BitmapFactory.decodeFile(file_go.getAbsolutePath(), myoptions);
//根据在图片的宽和高，得到图片在不变形的情况指定大小下的缩略图,设置宽为222；
        int height = myoptions.outHeight * 512 / myoptions.outWidth;
        myoptions.outWidth = 512;
        myoptions.outHeight = height;
//在重新设置玩图片显示的高和宽后记住要修改，Options对象inJustDecodeBounds的属性为false;
//不然无法显示图片;
        myoptions.inJustDecodeBounds = false;
//还没完这里才刚开始,要节约内存还需要几个属性，下面是最关键的一个；
        myoptions.inSampleSize = myoptions.outWidth / 222;
//还可以设置其他几个属性用于缩小内存；
        myoptions.inPurgeable = true;
        myoptions.inInputShareable = true;
        myoptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// 默认是Bitmap.Config.ARGB_8888
//成功了，下面就显示图片咯；
        final Bitmap bitmat = BitmapFactory.decodeFile(file_go.getAbsolutePath(), myoptions);
        zujian.img.setImageBitmap(bitmat);
        zujian.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CDialog3.Builder builder = new CDialog3.Builder(context);
                builder.setTitle(data.get(position).getName());
                builder.setpp(bitmat);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }

                });
                builder.create().show();
            }
        });
        zujian.add.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()== MotionEvent.ACTION_UP){
                    index=position;
                }
                return false;
            }
        });

        zujian.add.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //设置焦点监听，当获取到焦点的时候才给它设置内容变化监听解决卡的问题

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et=(EditText) v;
                if(mWatcher==null){
                    mWatcher=new myWatcher();
                }
                if(hasFocus){
                    et.addTextChangedListener(mWatcher);//设置edittext内容监听
                }else {
                    et.removeTextChangedListener(mWatcher);
                }

            }
        });

        zujian.add.clearFocus();//防止点击以后弹出键盘，重新getview导致的焦点丢失
        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            zujian.add.requestFocus();
        }
        zujian.add.setNumber(num[position]);//这一定要放在clearFocus()之后，否则最后输入的内容在拉回来时会消失

        return convertView;
    }

    class myWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub


        }

        @Override
        public void afterTextChanged(Editable s) {
            num[index]=Integer.valueOf(s.toString());//为输入的位置内容设置数组管理器，防止item重用机制导致的上下内容一样的问题
        }

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
    public boolean string2Image(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null)
            return false;
        try {
            // Base64解码
            byte[] b = Base64.decode(imgStr.getBytes(), Base64.DEFAULT);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成Jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
