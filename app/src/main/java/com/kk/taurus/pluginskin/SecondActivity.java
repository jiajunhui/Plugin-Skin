package com.kk.taurus.pluginskin;

import android.os.Bundle;
import android.view.View;

import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.loader.SkinManager;

public class SecondActivity extends SkinBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("skin.plugin", new ILoaderListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess() {

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
    }
}
