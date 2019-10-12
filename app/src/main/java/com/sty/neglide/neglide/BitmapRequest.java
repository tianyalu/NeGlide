package com.sty.neglide.neglide;

import android.content.Context;
import android.widget.ImageView;

import com.sty.neglide.MD5Utils;

import java.lang.ref.SoftReference;

public class BitmapRequest {
    //请求地址
    private String url;
    //上下文
    private Context context;
    //需要加载图片的控件
    private SoftReference<ImageView> imageView;
    //设置占位图片
    private int resId;
    //回调对象
    private RequestListener requestListener;
    //图片的标识
    private String urlMd5;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url =  url;
        this.urlMd5 = MD5Utils.toMD5(url);
        return this;
    }

    public BitmapRequest loading(int resId) {
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener) {
        this.requestListener = listener;
        return this;
    }

    //显示图片的控件
    public void into(ImageView imageView) {
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public int getResId() {
        return resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }
}
