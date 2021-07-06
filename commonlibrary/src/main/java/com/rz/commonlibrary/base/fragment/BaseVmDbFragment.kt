package com.rz.commonlibrary.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rz.commonlibrary.base.viewmodel.BaseViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 12:23 下午
 * @Description:    ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了
 * 需要使用Databind的请继承它
 */
abstract class BaseVmDbFragment<VM:BaseViewModel,DB: ViewDataBinding>: BaseVmFragment<VM>() {
    //该类绑定的ViewDataBinding
    lateinit var mDatabind: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDatabind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }
}