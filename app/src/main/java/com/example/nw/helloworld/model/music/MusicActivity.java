package com.example.nw.helloworld.model.music;

import android.view.View;

import com.example.nw.helloworld.R;

import butterknife.OnClick;
import chen.wentong.commonlib.base.BaseActivity;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：
 */

public class MusicActivity extends BaseActivity{


    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_load_local_music_list})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.btn_load_local_music_list:

                break;

            case R.id.btn_player_assets_music:

                break;
        }
    }
}
