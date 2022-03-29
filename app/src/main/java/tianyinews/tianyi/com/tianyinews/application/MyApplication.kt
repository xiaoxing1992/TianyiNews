package tianyinews.tianyi.com.tianyinews.application

import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import cn.smssdk.SMSSDK
import com.google.android.gms.ads.MobileAds
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.rz.commonlibrary.base.BaseApp
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import tianyinews.tianyi.com.tianyinews.activity.MainActivity
import tianyinews.tianyi.com.tianyinews.activity.SplashActivity
import tianyinews.tianyi.com.tianyinews.weight.EmptyCallback
import tianyinews.tianyi.com.tianyinews.weight.ErrorCallback
import tianyinews.tianyi.com.tianyinews.weight.LoadingCallback

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */
class MyApplication : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        myApplication = this
    }
    companion object {
        private var myApplication: MyApplication? = null
    }
}