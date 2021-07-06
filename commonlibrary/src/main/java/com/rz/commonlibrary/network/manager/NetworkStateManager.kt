package com.rz.commonlibrary.network.manager

import com.rz.commonlibrary.base.callback.livedata.EventLiveData

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 12:19 下午
 * @Description:    网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }
}