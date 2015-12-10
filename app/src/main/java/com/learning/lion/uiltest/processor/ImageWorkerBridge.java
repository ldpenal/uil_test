package com.learning.lion.uiltest.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.learning.lion.uiltest.R;
import com.learning.lion.uiltest.test.SimpleImageLoader;
import com.learning.lion.uiltest.test.SnapImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by lion on 12/7/15.
 */
public class ImageWorkerBridge extends ImageLoaderConfigurationBase implements DisplayerInterface {

    private Context context;

    private SnapImageLoader snapImageLoader;
    private SimpleImageLoader simpleImageLoader;

    public static DisplayImageOptions defaultOptions;
    public static DisplayImageOptions snapOptions;
    public static DisplayImageOptions imageOptions;
    public static DisplayImageOptions temporaryOptions;

    public static ImageLoaderConfiguration snapConfiguration;
    public static ImageLoaderConfiguration imageConfiguration;

    static {
        defaultOptions = buildDefaultDisplayOptions();
        snapOptions = buildSnapDisplayOptions();
        imageOptions = buildImageDisplayOptions();
        temporaryOptions = buildTemporaryOptions();
    }

    /**
     *
     * @param context
     */
    public ImageWorkerBridge(Context context) {
        this.context = context;

        imageConfiguration = buildDefaultLoaderConfig(context, defaultOptions);
        snapConfiguration = buildSnapLoaderConfiguration();

        snapImageLoader = SnapImageLoader.getInstance();
        snapImageLoader.init(snapConfiguration);

        simpleImageLoader = SimpleImageLoader.getInstance();
        simpleImageLoader.init(imageConfiguration);
    }

    /**
     * @return
     */
    public static DisplayImageOptions buildSnapDisplayOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(false)
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
     * @return
     */
    public static DisplayImageOptions buildImageDisplayOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();

        return displayImageOptions;
    }

    /**
     * @return
     */
    public static DisplayImageOptions buildTemporaryOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(100))
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();

        return displayImageOptions;
    }

    public ImageLoaderConfiguration buildSnapLoaderConfiguration() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);

        builder = builder.defaultDisplayImageOptions(snapOptions)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .imageDownloader(new SnapDownloader(context, Variables.HTTP_CONNECTION_TIMEOUT, Variables.HTTP_READ_TIMEOUT))
                .threadPoolSize(Variables.SIZE_THREAD_POOL);

        ImageLoaderConfiguration configuration = builder.build();

        return configuration;
    }

    public void reInitialize(ImageLoaderConfiguration newImageLoaderConfiguration) {
        this.imageConfiguration = newImageLoaderConfiguration;
    }

    @Override
    public void showSnap(String uri, ImageView imageView) {
        snapImageLoader.displayImage(uri, imageView);
    }

    @Override
    public void showSnap(String uri, ImageView imageView, ImageLoadingListener imageLoadingListener) {
        snapImageLoader.displayImage(uri, imageView, imageLoadingListener);
    }

    @Override
    public void showImage(String uri, ImageView imageView) {
        simpleImageLoader.displayImage(uri, imageView);
    }

    @Override
    public void showImage(String uri, ImageView imageView, ImageLoadingListener imageLoadingListener) {
        simpleImageLoader.displayImage(uri, imageView, imageLoadingListener);
    }

    public static DisplayImageOptions getSnapOptions() {
        return snapOptions;
    }

    public SnapImageLoader getSnapImageLoader() {
        return snapImageLoader;
    }

    public SimpleImageLoader getSimpleImageLoader() {
        return simpleImageLoader;
    }

    public void clearDiskCache(ImageLoader imageLoader) {
        imageLoader.clearDiskCache();
    }

    protected static class SnapDownloader extends BaseImageDownloader {

        public SnapDownloader(Context context) {
            super(context);
        }

        public SnapDownloader(Context context, int connectTimeout, int readTimeout) {
            super(context, connectTimeout, readTimeout);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
            HttpURLConnection conn = super.createConnection(url, extra);
            Map<String, String> headers = (Map<String, String>) extra;
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            // conn.setRequestProperty("Authorization", "Bearer " + preferencesManager.getToken());
            return conn;
        }
    }
}
