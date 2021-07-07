package com.rz.commonlibrary.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import com.rz.commonlibrary.ext.getVmClazz
import com.rz.commonlibrary.network.manager.NetState
import com.rz.commonlibrary.network.manager.NetworkStateManager

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 4:57 下午
 * @Description:    ViewModelActivity基类，把ViewModel注入进来了
 */
abstract class BaseVmActivity<VM:BaseViewModel>: AppCompatActivity() {

    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    private var isUserDb = false

    lateinit var mViewModel: VM

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!isUserDb){
            setContentView(layoutId())
        }else{
            initDataBind()
        }
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this, Observer {
            showLoading(it)
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
    }


    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()


    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }


    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}
}