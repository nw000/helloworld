package com.example.nw.helloworld.model.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.example.nw.helloworld.R;
import com.example.nw.helloworld.adapter.StringTestAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chen.wentong.commonlib.base.BaseActivity;
import chen.wentong.commonlib.net.subscriber.AbsObjectSubscriber;
import chen.wentong.commonlib.widget.TitleBar;

/**
 * Created by chenwentong on 2018/2/11.
 */

public class BlueToothActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.btn_search_bluetooth)
    Button mBtnSearchBluetooth;
    @BindView(R.id.btn_toggle_bluetooth)
    Button mBtnToggleBluetooth;
    @BindView(R.id.rv_blue_tooth)
    RecyclerView mRvBlueTooth;
    private StringTestAdapter mAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> mBluetoothName = new ArrayList<>();
    private BroadcastReceiver foundBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {  //蓝牙搜索完成
                showLongToast("蓝牙搜索完成");
            } else {                                                          //发现蓝牙设备
                showLongToast("蓝牙搜索到新设备");
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) { //已配对设备
                    mAdapter.addData("已配对设备 \n 名称是 ：" + device.getName() + "\n地址是："
                            + device.getAddress());
                } else {                                                    //为配对设备
                    mAdapter.addData("没有配对过的设备 \n 名称是 ：" + device.getName() + "\n地址是："
                            + device.getAddress());
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvBlueTooth.setLayoutManager(linearLayoutManager);
        mAdapter = new StringTestAdapter();
        mRvBlueTooth.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        //获取蓝牙adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        registerReceiver(foundBluetoothReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
        registerReceiver(foundBluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            LogUtils.d("蓝牙打开成功");
        }
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new AbsObjectSubscriber<Boolean>() {
                    @Override
                    public void onFailure(Throwable e) {
                        LogUtils.d(TAG, "蓝牙授权失败");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        LogUtils.d(TAG, "蓝牙授权成功");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(foundBluetoothReceiver);
        super.onDestroy();
    }

    @OnClick({R.id.btn_search_bluetooth, R.id.btn_toggle_bluetooth})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.btn_search_bluetooth:
                mBluetoothAdapter.startDiscovery();
                break;
            case R.id.btn_toggle_bluetooth:
                if (mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                } else {
                    mBluetoothAdapter.enable();
                }
                break;
        }
    }

}
