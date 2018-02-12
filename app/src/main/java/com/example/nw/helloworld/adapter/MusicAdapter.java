package com.example.nw.helloworld.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nw.helloworld.R;

import chen.wentong.commonlib.player.controler.Mp3Info;

/**
 * Created by wentong.chen on 18/2/6.
 * 功能：
 */

public class MusicAdapter extends BaseQuickAdapter<Mp3Info, BaseViewHolder> {
    public MusicAdapter() {
        super(R.layout.item_music, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Mp3Info item) {
        helper.setText(R.id.tv_music_name, item.getTitle())
                .setText(R.id.tv_music_single, item.getArtist())
                .setText(R.id.tv_music_url, item.getUrl());
    }
}
