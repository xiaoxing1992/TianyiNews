package tianyinews.tianyi.com.tianyinews.application;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.smssdk.SMSSDK;
import tianyinews.tianyi.com.tianyinews.R;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    {

        PlatformConfig.setQQZone("1106040270", "toW1iueMzVMng43j");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        imageLoaderInit();
        myApplication = this;
        UMShareAPI.get(this);
        x.Ext.init(this);
        SMSSDK.initSDK(this, "1c65790672734", "bd4224b54238bd1a08663da7db8bfd70");
    }


    private void imageLoaderInit() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.not_found_loading)
                .showImageOnFail(R.drawable.not_found_loading)
                .showImageOnLoading(R.drawable.not_found_loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //        .displayer(new RoundedBitmapDisplayer(20))  //这三句会产生图片不加载
                //至于为什么   请高手赐教
                //    .showImageOnFail(R.mipmap.ic_launcher)
                //   .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration loaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSizePercentage(20).diskCacheFileCount(1000).diskCacheSize(5 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .memoryCacheExtraOptions(480, 800)
                .build();
        ImageLoader.getInstance().init(loaderConfiguration);
    }
}
