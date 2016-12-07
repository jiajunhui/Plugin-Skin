package com.kk.taurus.pluginskin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xapp.jjh.logtools.tools.XLog;

import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.loader.SkinManager;

public class MainActivity extends SkinBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean darkStatus = SkinManager.getInstance().getBool("dark_status");
        XLog.d("dark_status = " + darkStatus);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },500);

        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("skin.plugin", new ILoaderListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess() {
                        int color = SkinManager.getInstance().getColorByResName("tv_test_layout_bg");
                        XLog.d("color = " + Integer.toHexString(color));
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        System.out.println("error : " + errMsg);
                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
            }
        });

        findViewById(R.id.tv_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().restoreDefaultTheme();
            }
        });

        findViewById(R.id.tv_font_hksnt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadFont("HKSN.ttf");
            }
        });

        findViewById(R.id.tv_font_kt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadFont("KT.ttf");
            }
        });

        findViewById(R.id.tv_font_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadFont(null);
            }
        });

        findViewById(R.id.tv_next_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
            }
        });
    }

}
