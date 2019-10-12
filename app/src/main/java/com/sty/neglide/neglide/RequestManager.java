package com.sty.neglide.neglide;

import java.util.concurrent.LinkedBlockingDeque;

public class RequestManager {
    private static RequestManager requestManager = new RequestManager();

    private RequestManager() {
        start();
    }

    private void start() {
        stop();
        startAllDispatcher();
    }

    private void stop() {
        if(bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if(!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.isInterrupted();
                }
            }
        }
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    //创建队列
    private LinkedBlockingDeque<BitmapRequest> requestQueue = new LinkedBlockingDeque<>();

    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if(bitmapRequest == null) {
            return;
        }
        if(!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }

    //创建BitmapDispatchers(创建多个柜台)
    private BitmapDispatcher[] bitmapDispatchers;

    private void startAllDispatcher() {
        //获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();

            bitmapDispatchers[i] = bitmapDispatcher;
        }

    }
}
