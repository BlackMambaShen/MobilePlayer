package com.example.liang.mobileplayer2.pager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liang.mobileplayer2.MainActivity;
import com.example.liang.mobileplayer2.R;
import com.example.liang.mobileplayer2.base.BasePager;
import com.example.liang.mobileplayer2.domain.MediaItem;

import java.util.ArrayList;
import java.util.Formatter;

public class VideoPager extends BasePager {
    private Activity activity;
    public VideoPager(Activity activity){
        super(activity);
        this.activity=activity;
    }

    private RecyclerView recyclerView;
    private TextView tv_nomedia;
    private ProgressBar pb_loading;
    private ArrayList<MediaItem>mediaItems;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems!=null&&mediaItems.size()>0){
                //有数据 设置适配器 隐藏文本 progressBar
                recyclerView.setAdapter(new VideoPagerAdapter(mContext));
                tv_nomedia.setVisibility(View.GONE);
            }else {
                //没数据 隐藏progressBar
                tv_nomedia.setVisibility(View.VISIBLE);
            }
            pb_loading.setVisibility(View.GONE);
        }
    };

    class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.ViewHolder>{
        private LayoutInflater mInflater;
        private Context mContext;
        public VideoPagerAdapter(Context context){
            this.mContext=context;
            this.mInflater=LayoutInflater.from(context);
        }
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_video_pager, parent, false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MediaItem mediaItem = mediaItems.get(position);
            holder.tv_name.setText(mediaItem.getName());
            holder.tv_size.setText(android.text.format.Formatter.formatFileSize(mContext,mediaItem.getSize()));

        }

        @Override
        public int getItemCount() {
            return mediaItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView iv_icon;
            TextView tv_name;
            TextView tv_time;
            TextView tv_size;
            public ViewHolder(View itemView) {
                super(itemView);
                 iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                tv_size = (TextView) itemView.findViewById(R.id.tv_size);

            }
        }
    }

    public VideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.video_pager, null);
         tv_nomedia = (TextView)view.findViewById(R.id.tv_nomedia);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler View);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromLocal();
    }

    //加载本地视频
    private void getDataFromLocal() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    mediaItems=new ArrayList<MediaItem>();
                    ContentResolver resolver = mContext.getContentResolver();
                    Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                    String[] objs={MediaStore.Video.Media.DISPLAY_NAME,
                            MediaStore.Video.Media.DURATION,
                            MediaStore.Video.Media.SIZE,
                            MediaStore.Video.Media.DATA,
                            MediaStore.Video.Media.ARTIST,
                    };
                    Cursor cursor = resolver.query(uri, objs, null, null, null);
                    if (cursor!=null){
                        while (cursor.moveToNext()){
                            MediaItem mediaItem=new MediaItem();
                            String name = cursor.getString(0);
                            mediaItem.setName(name);
                            long duration = cursor.getLong(1);
                            mediaItem.setDuration(duration);
                            long size = cursor.getLong(2);
                            mediaItem.setSize(size);
                            String data = cursor.getString(3);
                            mediaItem.setData(data);
                            String artist = cursor.getString(4);
                            mediaItem.setArtist(artist);
                            mediaItems.add(mediaItem);
                            System.out.println("翻皮皮"+mediaItem.toString());
                        }
                        cursor.close();
                    }
                    //发消息
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }
}
