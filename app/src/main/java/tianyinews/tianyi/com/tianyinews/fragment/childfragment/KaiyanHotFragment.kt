package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SizeUtils
import com.rz.commonlibrary.base.appContext
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.CategoryHotItem
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKaiyanHotBinding
import tianyinews.tianyi.com.tianyinews.view.DotPagerIndicator
import tianyinews.tianyi.com.tianyinews.view.ZZCommonNavigatorAdapter
import tianyinews.tianyi.com.tianyinews.viewmodel.KaiyanHotViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 2:45 下午
 * @Description:
 */
class KaiyanHotFragment : BaseFragment<KaiyanHotViewModel, FragmentKaiyanHotBinding>() {

    private var alldata: MutableList<CategoryHotItem>? = null


    private val adapter: MyHomeListViewPager by lazy { MyHomeListViewPager(childFragmentManager) }

    override fun layoutId(): Int = R.layout.fragment_kaiyan_hot

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.requestData()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.run {
            liveData.observe(viewLifecycleOwner, Observer {
                alldata = it.listData?.toMutableList()
                mDatabind.viewPager?.adapter = adapter
                val commonNavigatorAdapter = ZZCommonNavigatorAdapter(alldata, mDatabind.viewPager)
                val commonNavigator = CommonNavigator(requireActivity()).apply {
                    scrollPivotX = 0.8f
                    leftPadding = SizeUtils.dp2px(36f)
                    rightPadding = SizeUtils.dp2px(36f)
                    isAdjustMode = true
                }
                commonNavigator.adapter = commonNavigatorAdapter
                mDatabind.magicindicator.navigator = commonNavigator
                ViewPagerHelper.bind(mDatabind.magicindicator, mDatabind.viewPager)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    inner class MyHomeListViewPager(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return KaiyanHotChildFragment.newInstance(alldata?.get(position)?.apiUrl ?: "")
        }

        override fun getCount(): Int {
            return alldata?.size ?: 0
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return alldata?.get(position)?.name ?: ""
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): KaiyanHotFragment {
            val fragment = KaiyanHotFragment()
            return fragment
        }
    }
}