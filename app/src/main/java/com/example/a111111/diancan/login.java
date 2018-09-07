package com.example.a111111.diancan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class login extends AppCompatActivity implements GestureDetector.OnGestureListener{
    public TextView btn;
    public EditText ans,code;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static boolean yy;
    public  GestureDetector detector;
    public String base_url="http://39.107.93.96/";
    private final OkHttpClient client = new OkHttpClient();
    public LinearLayout a;
    String tt="";

    static String sspp="";
    static public String user;
    static String token1;
    static double target=0;
    static SharedHelper sh;
    static public  String ln,fn,ag;
    static int level;

    private Context mContext;
    static int pre,a1,a2,a3,a4,a5;
    public class erro {
        private int errno;
        private String token;
        private  int level;
        private String lastname;
        private String FirstName;
        private String age;
        erro(int errno,String token,int level,String lastname,String firstname,String age){
            this.errno = errno;
            this.token = token;
            this.level=level;
            this.age=age;
            this.FirstName=firstname;
            this.lastname=lastname;
        }
    }
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            //这里的话如果接受到信息码是123
            switch (msg.what) {
                case 0:
                    if (sh.read(user+"tt").get(user+"tt").equals("")) {

                        sh.save(user+"tt", "1");
                    }
                    if (sh.read(user+"le").get(user+"le").equals("")) {

                        sh.save(user+"le", "()");
                    }

                    Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, welcome.class);
                    Pair first = new Pair<>(btn, ViewCompat.getTransitionName(btn));
                    ActivityOptionsCompat transitionActivityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(login.this, first);
                    ActivityCompat.startActivity(login.this,
                            intent, transitionActivityOptions.toBundle());
                    break;
                case 1:
                    Toast.makeText(login.this, "账号不存在", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(login.this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(login.this, "未知错误", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_login);
        SysApplication.getInstance().addActivity(this);
        a=(LinearLayout)  findViewById(R.id.a);
        a1=1;a2=1;a3=1;a4=1;a5=1;yy=false;
        pre=4;

        detector=new GestureDetector(this,this);
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);

        btn=(TextView) findViewById(R.id.btnThree);

        ans=(EditText) findViewById(R.id.ans);
        code=(EditText) findViewById(R.id.code);
        if (sh.read("username").get("username")!=null) ans.setText(sh.read("username").get("username"));
        if (sh.read("password").get("password")!=null) code.setText(sh.read("password").get("password"));
        sh.save("main","1");
        // 这一步必须要做,否则不会显示.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder pa=new  FormBody.Builder();
                            pa.add("id",ans.getText().toString());
                            pa.add("pwd",code.getText().toString());
                            sh.save("username",ans.getText().toString());
                            sh.save("password",code.getText().toString());
                            tt=post(pa,"login.php");
                            System.out.println(ans.getText().toString());
                            System.out.println(code.getText().toString());
                            System.out.println(tt);
                            Gson gson = new Gson();
                            int er = gson.fromJson(tt,erro.class).errno;
                            token1=gson.fromJson(tt,erro.class).token;
                            level=gson.fromJson(tt,erro.class).level;
                            fn=gson.fromJson(tt,erro.class).FirstName;
                            ln=gson.fromJson(tt,erro.class).lastname;
                            ag=gson.fromJson(tt,erro.class).age;
                            if (er==0)
                            {
                                user=ans.getText().toString();
                                hand.sendEmptyMessage(0);

                            }
                            else if (er==1) hand.sendEmptyMessage(1);
                            else if (er==2) hand.sendEmptyMessage(2);
                            else if (er==3) hand.sendEmptyMessage(3);
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

        System.out.println("liuenbeiwansui");
        try{
            Request request = new Request.Builder()
                    .url(base_url+UR)
                    .post(pa.build())
                    .build();
        /*Request request = new Request.Builder()
                .url(base_url+UR+"?id=1mmm&pwd=7539100")
                .get()
                .build();*/

            Response response = client.newCall(request).execute();
            System.out.println("liuenbeiwansui");
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            else {
                return response.body().string();

            }
        }catch (Exception e){
            System.out.println("liuenbeizhizhang");
            System.out.println(e);
        }
    return "zhizhang";
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
        if (arg0.getX() > arg1.getX() + 50)
        {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);
            login.this.finish();
        }


        return  false;
    }
    @Override
    public	void	onLongPress(MotionEvent	arg0)	{
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

