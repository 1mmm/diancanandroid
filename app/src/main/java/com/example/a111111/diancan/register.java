package com.example.a111111.diancan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class register extends AppCompatActivity implements GestureDetector.OnGestureListener{
    public Button btn;
    public EditText ans,code,codea;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    public LinearLayout a;
    public GestureDetector detector;
    String tt="";
    private Context mContext;
    public class erro {
        private int errno;
        erro(int errno){
            this.errno = errno;
        }
    }
    int errno;
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 0:
                    alert = null;
                    builder = new AlertDialog.Builder(register.this);
                    alert = builder
                            .setTitle("注册成功！")
                            .setMessage("恭喜注册成功，立即登录吧")
                            .setPositiveButton("确定", null)
                            .create();             //创建AlertDialog对象
                    alert.show();
                    break;
                case 1:
                    Toast.makeText(register.this, "账号已被注册", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(register.this, "未知错误", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(register.this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            SysApplication.getInstance().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SysApplication.getInstance().addActivity(this);
        a=(LinearLayout)  findViewById(R.id.a);
        mContext=getApplicationContext();
        btn=(Button) findViewById(R.id.btnThree);
        detector=new GestureDetector(this,this);
        ans=(EditText) findViewById(R.id.ans);
        code=(EditText) findViewById(R.id.code);
        codea=(EditText) findViewById(R.id.codea);
        // 这一步必须要做,否则不会显示.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            if (codea.getText().toString().equals(code.getText().toString())) {
                                FormBody.Builder pa = new FormBody.Builder();
                                pa.add("nichen", ans.getText().toString());
                                pa.add("code", code.getText().toString());
                                pa.add("lastname", "unknown");
                                pa.add("firstname", "unknown");
                                pa.add("age", "100");
                                pa.add("level", "1");
                                tt = post(pa, "registor.php");
                                Gson gson = new Gson();
                                int er = gson.fromJson(tt, erro.class).errno;
                                if (er == 0) hand.sendEmptyMessage(0);
                                else if (er == 1) hand.sendEmptyMessage(1);
                                else if (er == 2) hand.sendEmptyMessage(2);
                            }
                            else hand.sendEmptyMessage(3);
                        }
                        catch (Exception e)
                        {
                            hand.sendEmptyMessage(2);
                        }
                    }


                }.start();



            }
        });
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
    public	boolean	onTouchEvent(MotionEvent	event)	{
//	TODO	Auto-generated	method	stub
//将该Activity上触碰事件交给GestureDetector处理
        return	detector.onTouchEvent(event);
    }
    @Override
    public	boolean	onDown(MotionEvent	arg0)	{
//	TODO	Auto-generated	method	stub
        return	false;
    }
    @Override
    public	boolean	onFling(MotionEvent arg0, MotionEvent	arg1, float	arg2,
                                 float	arg3) {

//	TODO	Auto-generated	method	stub
//当是Fragment0的时候
        if (arg1.getX() > arg0.getX() + 50)
        {
            Intent intent = new Intent(register.this, login.class);
            startActivity(intent);
            register.this.finish();
        }


        return  false;
    }
    @Override
    public	void	onLongPress(MotionEvent arg0)	{
//	TODO	Auto-generated	method	stub

    }

    @Override
    public	boolean	onScroll(MotionEvent	arg0,	MotionEvent	arg1,	float	arg2,
                                  float	arg3)	{
//	TODO	Auto-generated	method	stub
        return	false;
    }

    @Override
    public	void	onShowPress(MotionEvent arg0)	{
//	TODO	Auto-generated	method	stub

    }

    @Override
    public	boolean	onSingleTapUp(MotionEvent	arg0)	{
//	TODO	Auto-generated	method	stub
        return	false;
    }
}
