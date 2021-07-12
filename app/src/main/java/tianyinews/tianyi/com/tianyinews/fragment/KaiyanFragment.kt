package tianyinews.tianyi.com.tianyinews.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.jzvd.Jzvd
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.appContext
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKaiyanBinding
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.KaiyanCategoryFragment
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.KaiyanHomeFragment
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.KaiyanHotFragment
import tianyinews.tianyi.com.tianyinews.view.MBCommonNavigatorAdapter
import tianyinews.tianyi.com.tianyinews.viewmodel.KaiyanViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 4:39 下午
 * @Description:
 */
class KaiyanFragment : BaseFragment<KaiyanViewModel, FragmentKaiyanBinding>() {

    private val alldata: MutableList<MyChannel> by lazy {
        val open = appContext.resources.openRawResource(R.raw.kaiyan_list)
        val json = ConvertUtils.inputStream2String(open, "UTF-8")
        JSON.parseArray(json, MyChannel::class.java)
    }

    private val commonNavigator: CommonNavigator by lazy { CommonNavigator(requireActivity()).apply { scrollPivotX = 0.8f } }

    private val adapter: MyHomeListViewPager by lazy { MyHomeListViewPager(childFragmentManager) }

    override fun layoutId(): Int = R.layout.fragment_kaiyan
    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .statusBarColorTransformEnable(false)
            .statusBarColor(R.color.white).statusBarDarkFont(true).init()

        mDatabind.viewPager.adapter = adapter
        val commonNavigatorAdapter = MBCommonNavigatorAdapter(
            alldata,
            mDatabind.viewPager,
            MBCommonNavigatorAdapter.TYPE_TITLE_COLORFLIP,
            WrapPagerIndicator(requireContext()).apply {
                fillColor = Color.parseColor("#ebe4e3")
                horizontalPadding = SizeUtils.dp2px(8f)
                verticalPadding = SizeUtils.dp2px(4f)
//                mode = LinePagerIndicator.MODE_EXACTLY
//                lineHeight = UIUtil.dip2px(context, 6.0).toFloat()
//                lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
//                roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
//                startInterpolator = AccelerateInterpolator()
//                endInterpolator = DecelerateInterpolator(2.0f)
//                setColors(Color.parseColor("#D43D3D"))
            }
        )
        commonNavigator.adapter = commonNavigatorAdapter
        mDatabind.magicindicator.navigator = commonNavigator
//        commonNavigatorAdapter.notifyDataSetChanged()
        ViewPagerHelper.bind(mDatabind.magicindicator, mDatabind.viewPager)

        mDatabind.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                Jzvd.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.white).statusBarDarkFont(true).init()
        }
    }

    inner class MyHomeListViewPager(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            when (alldata!![position].type) {
                "1" -> {
                    return KaiyanHomeFragment.newInstance()
                }
                "2" -> {
                    return KaiyanCategoryFragment.newInstance()
                }
                "3" -> {
                    return KaiyanHotFragment.newInstance()
                }
            }
            return KaiyanHomeFragment.newInstance()
        }

        override fun getCount(): Int {
            return alldata!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return alldata!![position].title
        }
    }
}