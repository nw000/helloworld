package com.example.nw.helloworld.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class TestStringPagerAdapter extends PagerAdapter {
    private List<RecyclerView> mData;
    public TestStringPagerAdapter(List<RecyclerView> list) {
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        con
    }
}
