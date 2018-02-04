package com.example.nw.helloworld;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import chen.wentong.commonlib.utils.DevicesUtil;
import chen.wentong.commonlib.widget.loadsir.callback.LoadManager;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DevicesUtil.init(this);
        LeakCanary.install(this);
        LoadManager.beginBuilder()
                .addCallback(new ErrorCallback())
                .commit();
    }
}
