package com.example.liang.mobileplayer2.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.liang.mobileplayer2.R;

public class titleBar extends LinearLayout implements View.OnClickListener {
    private View tv_search;
    private View rl_game;
    private View iv_record;
    private Context context;
    //在代码中实例化该类
    public titleBar(Context context) {
        this(context,null);
    }

    public titleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public titleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    /*
    布局文件加载完成，执行此方法
     */
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_search=getChildAt(1);
        rl_game=getChildAt(2);
        iv_record=getChildAt(3);
        //设置点击事件
        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_game:
                Toast.makeText(context, "游戏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_record:
                Toast.makeText(context, "播放历史", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
