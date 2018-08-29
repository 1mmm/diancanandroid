package com.example.a111111.diancan.gkd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.R;
import com.example.a111111.diancan.gkd.inside.cdmanager;
import static com.example.a111111.diancan.login.user;

public class manager extends AppCompatActivity {
    TextView cd,zh,wel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager2);
        cd=(TextView) findViewById(R.id.cd);
        zh=(TextView) findViewById(R.id.zh);
        wel=(TextView) findViewById(R.id.welcome);
        wel.setText(user+",欢迎回来！");
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
                Toast.makeText(manager.this, "成功呼叫服务员，请耐心等候！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
