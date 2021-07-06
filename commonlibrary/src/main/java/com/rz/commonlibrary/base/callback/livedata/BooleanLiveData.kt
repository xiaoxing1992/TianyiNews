package com.rz.commonlibrary.base.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 3:24 下午
 * @Description:
 */
class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}