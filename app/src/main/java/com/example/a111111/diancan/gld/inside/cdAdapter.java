package com.example.a111111.diancan.gld.inside;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import okhttp3.FormBody;

public class cdAdapter extends BaseAdapter {
    private LinkedList<cdlist> data;
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
        public Button xg,sc,tp;

    }

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
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(Ac,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0X11);
            //判断是否需要 向用户解释，为什么要申请该权限
            ActivityCompat.shouldShowRequestPermissionRationale(Ac,
                    android.Manifest.permission.READ_CONTACTS);
            //InputMethodManager  manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(Ac,
                    new String[]{android.Manifest.permission.CAMERA}, 0X11);
            //判断是否需要 向用户解释，为什么要申请该权限
            ActivityCompat.shouldShowRequestPermissionRationale(Ac,
                    android.Manifest.permission.READ_CONTACTS);
            //InputMethodManager  manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        Zujian zujian=null;
        zujian=new Zujian();
        //获得组件，实例化组件
        convertView=layoutInflater.inflate(R.layout.cdlist, parent,false);
        zujian.name=(TextView)convertView.findViewById(R.id.name);
        zujian.price=(TextView)convertView.findViewById(R.id.price);
        zujian.desc=(TextView)convertView.findViewById(R.id.desc);
        zujian.xg=(Button) convertView.findViewById(R.id.xg);
        zujian.sc=(Button) convertView.findViewById(R.id.sc);
        zujian.tp=(Button) convertView.findViewById(R.id.tp);
        //绑定数据
        String h=data.get(position).getName()+"";
        zujian.name.setText(h);
        h=data.get(position).getPrice()+"";
        zujian.price.setText(h);
        h=data.get(position).getDesc()+"";
        zujian.desc.setText(h);
        zujian.tp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!mars_file.exists()) {
                    mars_file.mkdirs();
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// 并设置拍照的存在方式为外部存储和存储的路径；
                String h=file_str + "/my_camera/file"+data.get(position).getId()+".png";
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
                Bitmap bitmat = BitmapFactory.decodeFile(file_go.getAbsolutePath(), myoptions);
                CDialog.Builder builder = new CDialog.Builder(context);
                builder.setTitle(data.get(position).getName());
                builder.setpp(bitmat);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                builder.create().show();
            }

        });
        return convertView;
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
