package com.learning.lion.uiltest.test;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lion on 12/7/15.
 */
public class SnapImageLoader extends ImageLoader {

    public static SnapImageLoader snapImageLoader = null;

    protected SnapImageLoader() {
        super();
    }

    public static SnapImageLoader getInstance() {
        if(snapImageLoader == null) {
            snapImageLoader = new SnapImageLoader();
        }

        return snapImageLoader;
    }
}
