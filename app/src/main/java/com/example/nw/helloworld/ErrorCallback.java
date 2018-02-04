package com.example.nw.helloworld;

import chen.wentong.commonlib.widget.loadsir.callback.Callback;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class ErrorCallback extends Callback {

    @Override
    protected int createView() {
        return R.layout.layout_error;
    }
}
