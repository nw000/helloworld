package com.example.nw.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import chen.wentong.commonlib.net.core.RequestManager;
import chen.wentong.commonlib.net.subscriber.AbsObjectSubscriber;
import chen.wentong.commonlib.widget.loadsir.callback.LoadManager;
import chen.wentong.commonlib.widget.loadsir.callback.LoadService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private LoadService mLoadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("test commit");
        mTv = findViewById(R.id.tv);
//        LoadManager loadManager = LoadManager.beginBuilder()
//                .addCallback(new ErrorCallback())
//                .builder();
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
//        RequestManager.getInstance().getNet(NetService.class)
//                .getUserInfos(20, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new AbsObjectSubscriber<List<UserInfo>>() {
//                    @Override
//                    public void onFailure(Throwable e) {
//                        Toast.makeText(getBaseContext(), e.getMessage() + " haha ", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(List<UserInfo> userInfos) {
//                        mTv.setText("getUserInfos" + mTv.getText() + userInfos.get(0).toString());
//                    }
//                });
    }
}
