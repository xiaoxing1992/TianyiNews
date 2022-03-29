package tianyinews.tianyi.com.tianyinews.initializer

import android.content.Context
import androidx.startup.Initializer
import cat.ereza.customactivityoncrash.config.CaocConfig
import cn.smssdk.SMSSDK
import com.google.android.gms.ads.MobileAds
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import tianyinews.tianyi.com.tianyinews.activity.MainActivity
import tianyinews.tianyi.com.tianyinews.activity.SplashActivity
import tianyinews.tianyi.com.tianyinews.initializer.processor.InitializeProcessor
import tianyinews.tianyi.com.tianyinews.weight.EmptyCallback
import tianyinews.tianyi.com.tianyinews.weight.ErrorCallback
import tianyinews.tianyi.com.tianyinews.weight.LoadingCallback

/**
 * @Author:         renzhengwei
 * @CreateDate:     2022/3/29 4:16 下午
 * @Description:
 */
class ApplicationInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val processor = InitializeProcessor()
        /**
         * =============================
         */
        processor.invoke {
            //界面加载管理 初始化
            LoadSir.beginBuilder().addCallback(LoadingCallback()) //加载
                .addCallback(ErrorCallback()) //错误
                .addCallback(EmptyCallback()) //空
                .setDefaultCallback(SuccessCallback::class.java).commit()
        }

        processor.invoke {
            //防止项目崩溃，崩溃后打开错误界面
            CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
                .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
                .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
                .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
                .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
                .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
                .restartActivity(SplashActivity::class.java) // 重启的activity
                .errorActivity(MainActivity::class.java) //发生错误跳转的activity
                .apply()
        }


        processor.async {
            MobileAds.initialize(context)
        }

        processor.async {
            UMShareAPI.get(context)
        }

        processor.async {
            SMSSDK.initSDK(context, "1c65790672734", "bd4224b54238bd1a08663da7db8bfd70")
        }

        processor.async {
            PlatformConfig.setQQZone("1106040270", "toW1iueMzVMng43j")
        }

        processor.start()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}