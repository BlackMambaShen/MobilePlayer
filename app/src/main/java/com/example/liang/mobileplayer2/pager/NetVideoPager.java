package com.example.liang.mobileplayer2.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.liang.mobileplayer2.base.BasePager;

public class NetVideoPager extends BasePager {
    private TextView textView;

    public NetVideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
         textView = new TextView(mContext);
         textView.setTextSize(25);
         textView.setGravity(Gravity.CENTER);
         textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("网络视频页面");
    }
}
