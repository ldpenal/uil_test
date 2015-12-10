package com.learning.lion.uiltest.processor;

import android.content.Context;
import android.graphics.Bitmap;

import com.learning.lion.uiltest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by lion on 12/7/15.
 */
public class ImageLoaderConfigurationBase {

    /**
     * @return
     */
    public static DisplayImageOptions buildDefaultDisplayOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(100))
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
        return displayImageOptions;
    }

    /**
     * @param context
     * @param defaultDisplayOptions
     * @return
     */
    public static ImageLoaderConfiguration buildDefaultLoaderConfig(Context context, DisplayImageOptions defaultDisplayOptions) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder = builder.defaultDisplayImageOptions(defaultDisplayOptions)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .imageDownloader(new ImageWorkerBridge.SnapDownloader(context, Variables.HTTP_CONNECTION_TIMEOUT, Variables.HTTP_READ_TIMEOUT))
                .threadPoolSize(Variables.SIZE_THREAD_POOL)
                .memoryCacheSize(Variables.SIZE_THREAD_POOL);

        ImageLoaderConfiguration imageLoaderConfiguration = builder.build();

        return imageLoaderConfiguration;
    }
}
