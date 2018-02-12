package com.example.nw.helloworld.viewstudy.coordinatorlayout_study;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.nw.helloworld.R;
import com.example.nw.helloworld.adapter.StringTestAdapter;

import butterknife.BindView;
import chen.wentong.commonlib.base.BaseActivity;
import chen.wentong.commonlib.widget.TitleBar;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：coordinatorlayout控件学习
 */

public class CoordlActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
//    @BindView(R.id.fabtn)
//    FloatingActionButton mFloatingActionButton;
    private StringTestAdapter mStringTestAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordl2; //coordinatorlayout的效果
    }

    @Override
    protected void initView() {
        ViewPager viewPager = new ViewPager(this);
//        viewPager.setAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        mStringTestAdapter = new StringTestAdapter();
        mStringTestAdapter.setNewData(20);
        rv.setAdapter(mStringTestAdapter);
    }

    @Override
    protected void initData() {

    }
}
