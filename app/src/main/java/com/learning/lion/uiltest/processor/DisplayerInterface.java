package com.learning.lion.uiltest.processor;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by lion on 12/7/15.
 */
public interface DisplayerInterface {

    void showSnap(String uri, ImageView imageView);
    void showImage(String uri, ImageView imageView);
    void showSnap(String uri, ImageView imageView, ImageLoadingListener imageLoadingListener);
    void showImage(String uri, ImageView imageView, ImageLoadingListener imageLoadingListener);
}
