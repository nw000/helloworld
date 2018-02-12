package com.example.nw.helloworld.model.music;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nw.helloworld.R;
import com.example.nw.helloworld.adapter.MusicAdapter;
import com.example.nw.helloworld.utils.MusicUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chen.wentong.commonlib.base.BaseActivity;
import chen.wentong.commonlib.net.subscriber.AbsObjectSubscriber;
import chen.wentong.commonlib.player.controler.Mp3Info;
import chen.wentong.commonlib.player.controler.MusicPlayerControler;
import chen.wentong.commonlib.player.controler.PlayMode;
import chen.wentong.commonlib.player.controler.Songinfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wentong.chen on 18/2/5.
 * 功能：
 */
public class MusicActivity extends BaseActivity{
    @BindView(R.id.rv)
    RecyclerView rv;
    private MusicAdapter mMusicAdapter;
    private MusicPlayerControler mMusicPlayerControler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initView() {
        mMusicPlayerControler = new MusicPlayerControler<Mp3Info>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        mMusicAdapter = new MusicAdapter();
        mMusicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMusicPlayerControler.play(0);
            }
        });
        mMusicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mMusicPlayerControler.play(0);
            }
        });
        rv.setAdapter(mMusicAdapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_load_local_music_list})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.btn_load_local_music_list:
                loadMusic();
                break;

            case R.id.btn_player_assets_music:

                break;
        }
    }

    public void loadMusic() {
        Observable.create(new ObservableOnSubscribe<List<Mp3Info>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Mp3Info>> e) throws Exception {
                List<Mp3Info> query = MusicUtil.getMp3Infos(getBaseContext());
                if (query != null && !query.isEmpty()) {
                    e.onNext(query);
                } else {
                    e.onError(new Exception("hhahahah"));
                }

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new AbsObjectSubscriber<List<Mp3Info>>() {
                    @Override
                    public void onFailure(Throwable e) {
                        showLongToast("扫描歌曲失败" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Mp3Info> songinfos) {
                        mMusicAdapter.setNewData(songinfos);
                        mMusicPlayerControler.setPlaymode(PlayMode.SINGLE_CYCLE_PLAY);
                        ArrayList<Songinfo<Mp3Info>> songinfos1 = new ArrayList<>();
                        Songinfo<Mp3Info> mp3InfoSonginfo = new Songinfo<>();
                        mp3InfoSonginfo.setUrl(songinfos.get(0).getUrl());
                        mp3InfoSonginfo.setT(songinfos.get(0));
                        songinfos1.add(mp3InfoSonginfo);
                        mMusicPlayerControler.setSongList(songinfos1);
                    }
                });
    }
}
