package com.rz.commonlibrary.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rz.commonlibrary.base.viewmodel.BaseViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 5:05 下午
 * @Description:
 */
abstract class BaseVmDbActivity<VM:BaseViewModel,DB:ViewDataBinding>:BaseVmActivity<VM>() {

    lateinit var mDatabind: DB


    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建DataBinding
     */
    override fun initDataBind() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId())
        mDatabind.lifecycleOwner = this
    }
}