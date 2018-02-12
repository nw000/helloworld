package com.example.nw.helloworld.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nw.helloworld.R;
import com.example.nw.helloworld.utils.TestUtils;

import java.util.List;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class StringTestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public StringTestAdapter() {
        super(R.layout.item_string_test, null);
    }

    public void setNewData(int size) {
        setNewData(TestUtils.getListStrings("StringTestAdapter", 100));
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
        helper.setBackgroundColor(R.id.tv, Color.RED);
    }
}
