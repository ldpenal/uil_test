package com.learning.lion.uiltest.test;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lion on 12/7/15.
 */
public class SimpleImageLoader extends ImageLoader {

    public static SimpleImageLoader simpleImageLoader = null;

    protected SimpleImageLoader() {
        super();
    }

    public static SimpleImageLoader getInstance() {
        if(simpleImageLoader == null) {
            simpleImageLoader = new SimpleImageLoader();
        }

        return simpleImageLoader;
    }
}
