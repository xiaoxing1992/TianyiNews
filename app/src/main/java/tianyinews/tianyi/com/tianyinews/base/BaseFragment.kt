package tianyinews.tianyi.com.tianyinews.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.rz.commonlibrary.base.fragment.BaseVmDbFragment
import com.rz.commonlibrary.base.viewmodel.BaseViewModel
import tianyinews.tianyi.com.tianyinews.ext.dismissLoadingExt
import tianyinews.tianyi.com.tianyinews.ext.hideSoftKeyboard
import tianyinews.tianyi.com.tianyinews.ext.showLoadingExt

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 2:07 下午
 * @Description:
 */
abstract class BaseFragment<VM:BaseViewModel,DB:ViewDataBinding>: BaseVmDbFragment<VM, DB>() {

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {
    }

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initListener() {
    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    override fun lazyLoadTime(): Long {
        return 300
    }

}