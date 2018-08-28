package com.example.a111111.diancan.gkd.inside;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a111111.diancan.R;


public class camanager extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private adder adv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camanager);
        adv = (adder) findViewById(R.id.adv_main);


        adv.setOnAddDelClickListener(new adder.OnAddDelClickListener() {
            @Override
            public void onAddClick(View v) {
                Log.i(TAG, "onAddClick: 执行");
                int origin = adv.getNumber();
                origin++;
                adv.setNumber(origin);
            }

            @Override
            public void onDelClick(View v) {
                int origin = adv.getNumber();
                origin--;
                adv.setNumber(origin);
            }
        });
    }
}

