package com.example.a111111.diancan.syd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;

import com.example.a111111.diancan.syd.inside.sycom;

import static com.example.a111111.diancan.login.user;

public class manager extends AppCompatActivity {
    TextView cd,zh,wel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager5);
        cd=(TextView) findViewById(R.id.cd);
        zh=(TextView) findViewById(R.id.dd);
        wel=(TextView) findViewById(R.id.welcome);
        wel.setText(user+",欢迎回来！");
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.a111111.diancan.syd.manager.this, sycom.class);
                startActivity(intent);
            }
        });
        zh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(com.example.a111111.diancan.syd.manager.this, "打印机正在打印，请稍后！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
