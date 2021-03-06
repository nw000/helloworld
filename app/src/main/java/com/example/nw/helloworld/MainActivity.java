package com.example.nw.helloworld;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nw.helloworld.model.bluetooth.BlueToothActivity;
import com.example.nw.helloworld.model.music.MusicActivity;
import com.example.nw.helloworld.viewstudy.coordinatorlayout_study.CoordlActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import chen.wentong.commonlib.base.BaseActivity;
import chen.wentong.commonlib.net.core.RequestManager;
import chen.wentong.commonlib.net.subscriber.AbsObjectSubscriber;
import chen.wentong.commonlib.widget.loadsir.callback.LoadManager;
import chen.wentong.commonlib.widget.loadsir.callback.LoadService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView mTv;
    private LoadService mLoadService;
    @BindView(R.id.btn_coordl)
    Button btn_coordl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new AbsObjectSubscriber<Boolean>() {
                    @Override
                    public void onFailure(Throwable e) {
                        showLongToast("权限申请失败");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            showLongToast("同意");
                        } else
                        {
                            showLongToast("不同意");
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        mLoadService = LoadManager.getDefault().register(this);
        RequestManager.getInstance().initBaseUrl("https://api.github.com", NetService.class);
        RequestManager.getInstance().getNet(NetService.class)
                .getUserInfo("test")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsObjectSubscriber<UserInfo>() {
                    @Override
                    public void onFailure(Throwable e) {
                        mLoadService.showCallback(ErrorCallback.class);
                        Toast.makeText(getBaseContext(), e.getMessage() + " error ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        mTv.setText("getUserInfo" + mTv.getText() + userInfo.toString());
                    }
                });
    }

    @OnClick({R.id.btn_coordl, R.id.btn_music, R.id.bluetooth})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.btn_coordl:
                startActivity(CoordlActivity.class);
                break;
            case R.id.btn_music:
                startActivity(MusicActivity.class);
                break;
            case R.id.bluetooth:
                startActivity(BlueToothActivity.class);
                break;
        }
    }
}
