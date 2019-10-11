package com.sty.neglide;

import android.graphics.Bitmap;

public interface RequestListener {
    void onSuccess(Bitmap bitmap);

    void onFailure();
}
