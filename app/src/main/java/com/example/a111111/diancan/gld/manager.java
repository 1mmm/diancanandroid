package com.example.a111111.diancan.gld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a111111.diancan.MainActivity;
import com.example.a111111.diancan.R;
import com.example.a111111.diancan.gld.inside.cdmanager;
import com.example.a111111.diancan.gld.inside.gkmanager;

public class manager extends AppCompatActivity {
    TextView cd,zh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        cd=(TextView) findViewById(R.id.cd);
        zh=(TextView) findViewById(R.id.zh);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manager.this, cdmanager.class);
                startActivity(intent);
            }
        });
        zh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manager.this, gkmanager.class);
                startActivity(intent);
            }
        });
    }
}
