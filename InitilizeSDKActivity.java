package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import java.util.Locale;

public class InitilizeSDKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initilize_sdkactivity);
        MultiDex.install(InitilizeSDKActivity.this);
        Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(InitilizeSDKActivity.this,Device_CategoryActivity.class));
            }
        },1000);

    }
}