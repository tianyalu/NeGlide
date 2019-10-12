package com.sty.neglide.neglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.sty.neglide.MyApplication;
import com.sty.neglide.cache.DoubleLruCache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 处理图片请求类
 */
public class BitmapDispatcher extends Thread{
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private LinkedBlockingDeque<BitmapRequest> requestQueue;

    //获取三级缓存对象
    private DoubleLruCache doubleLruCache = new DoubleLruCache(MyApplication.getMyApplication());

    public BitmapDispatcher(LinkedBlockingDeque<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                //去队列中获取请求
                BitmapRequest br = requestQueue.take();
                //处理请求
                //设置占位图片
                showLoadingImage(br);
                //网络加载图片
                Bitmap bitmap = findImage(br);
                //把图片加载到imageView
                showImage(br, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void showLoadingImage(BitmapRequest br) {
        if(br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView iv = br.getImageView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //设置占位图
                    iv.setImageResource(resId);
                }
            });
        }
    }

    private Bitmap findImage(BitmapRequest br) {
        //处理缓存的问题
        Bitmap bitmap = null;
        //去内存或者磁盘加载图片
        bitmap = doubleLruCache.get(br);

        //1.先让服务器将图片分为缩略图和原图
        //2.缩略图放到内存缓存及硬盘缓存，原图只放硬盘缓存
        //3.图片的后缀名全部改成“乱码”
        if(bitmap == null) {
            //网络加载图片
            bitmap = downloadImage(br.getUrl());
            if(bitmap != null) {
                doubleLruCache.put(br, bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //创建一个Url对象
            URL url = new URL(uri);
            //然后使用HttpURLConnection通过URL去开始读数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(is != null) {
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                if(fos != null) {
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    private void showImage(final BitmapRequest br, final Bitmap bitmap) {
        if(bitmap != null && br.getImageView() != null
                && br.getUrlMd5().equals(br.getImageView().getTag())){
            final ImageView iv = br.getImageView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageBitmap(bitmap);
                    if(br.getRequestListener() != null) {
                        RequestListener requestListener = br.getRequestListener();
                        requestListener.onSuccess(bitmap);
                    }
                }
            });
        }
    }




}
