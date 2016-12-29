package com.kk.taurus.pluginskin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xapp.jjh.logtools.tools.XLog;

import solid.ren.skinlibrary.attr.ResourceType;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.loader.SkinManager;

public class MainActivity extends SkinBaseActivity {

    private LinearLayout mLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinear = (LinearLayout) findViewById(R.id.ll_linear);

        TextView view1 = new TextView(this);
        view1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view1.setTextSize(TypedValue.COMPLEX_UNIT_PX,30);
        view1.setText("dynamic add text view1");
        mLinear.addView(view1);

        TextView view2 = new TextView(this);
        view2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view2.setTextSize(TypedValue.COMPLEX_UNIT_PX,30);
        view2.setText("dynamic add text view2");
        mLinear.addView(view2);

        dynamicAddView(view1, "textColor",R.color.tv_test_bg);
        dynamicAddView(view2, "textColor",R.color.tv_test_text_color);

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
