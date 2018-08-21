package com.example.liang.mobileplayer2.base;

import android.content.Context;
import android.view.View;

//基类
public abstract class BasePager {

    public final Context mContext;
    public View rootView;
    public boolean isInitData;

    public BasePager(Context context){
        this.mContext=context;
        rootView=initView();
    }

    //由孩子实现
    public abstract View initView();

    //需要数据的时候
    public void initData(){

    }
}
