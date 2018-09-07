package com.example.a111111.diancan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.a111111.diancan.ctd.inside.diningroom;

import static com.example.a111111.diancan.login.level;
public class MainActivity extends AppCompatActivity {
    TextView gkd,hcd,syd,gld,ctd;
    LinearLayout gk,sy,hc,gl,ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gkd=(TextView)findViewById(R.id.gkd);
        syd=(TextView)findViewById(R.id.syd);
        ctd=(TextView)findViewById(R.id.ctd);
        hcd=(TextView) findViewById(R.id.hcd);
        gld=(TextView) findViewById(R.id.gld);
        gk=(LinearLayout)findViewById(R.id.gk);
        sy=(LinearLayout)findViewById(R.id.sy);
        ct=(LinearLayout)findViewById(R.id.ct);
        hc=(LinearLayout) findViewById(R.id.hc);
        gl=(LinearLayout) findViewById(R.id.gl);
        switch (level) {
            case 1:
                gk.setVisibility(View.VISIBLE);
                break;
            case 2:
                sy.setVisibility(View.VISIBLE);
                break;
            case 3:
                hc.setVisibility(View.VISIBLE);
                break;
            case 4:
                ct.setVisibility(View.VISIBLE);
                break;
            case 5:
                gk.setVisibility(View.VISIBLE);
                ct.setVisibility(View.VISIBLE);
                hc.setVisibility(View.VISIBLE);
                sy.setVisibility(View.VISIBLE);
                gl.setVisibility(View.VISIBLE);
        }
        gld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a111111.diancan.gld.manager.class);
                startActivity(intent);
            }
        });
        gkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a111111.diancan.gkd.manager.class);
                startActivity(intent);
            }
        });
        ctd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a111111.diancan.ctd.manager.class);
                startActivity(intent);
            }
        });
        syd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a111111.diancan.syd.manager.class);
                startActivity(intent);
            }
        });
        hcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a111111.diancan.hcd.manager.class);
                startActivity(intent);
            }
        });


    }
}
