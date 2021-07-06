package com.rz.commonlibrary.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.rz.commonlibrary.base.callback.livedata.BooleanLiveData

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 3:23 下午
 * @Description:
 */
class KtxAppLifeObserver: LifecycleObserver {

    var isForeground = BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private  fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }

}