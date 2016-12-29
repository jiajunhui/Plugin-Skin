package com.kk.taurus.pluginskin;

import com.xapp.jjh.logtools.config.XLogConfig;
import com.xapp.jjh.logtools.logger.LogLevel;
import com.xapp.jjh.logtools.tools.XLog;

import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

/**
 * Created by Taurus on 16/12/4.
 */

public class App extends SkinBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        XLog.init(getApplicationContext(),new XLogConfig()
                .setLogLevel(LogLevel.FULL)
                .setMessageTable(true)
                .setLogDir(getExternalCacheDir())
                .setSaveCrashLog(true));
        SkinConfig.setGradientChangeColor(true);
    }
}
