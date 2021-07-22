package tianyinews.tianyi.com.tianyinews.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.jzvd.Jzvd
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ConvertUtils
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.appContext
import kotlinx.android.synthetic.main.fragment_video.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.databinding.FragmentVideoBinding
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.VideoChildFragment
import tianyinews.tianyi.com.tianyinews.util.GsonUtil
import tianyinews.tianyi.com.tianyinews.viewmodel.VideoViewModel
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */
class VideoFragment : BaseFragment<VideoViewModel, FragmentVideoBinding>() {

    private var fragList: ArrayList<BaseFragment<*, *>>? = null
    private val adapter: MyHomeListViewPager by lazy { MyHomeListViewPager(fragmentManager) }
    private var alldata: List<MyChannel> = mutableListOf()

    override fun layoutId(): Int = R.layout.fragment_video

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayBackground).statusBarDarkFont(true).init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayBackground).statusBarDarkFont(true).init()
        }
    }

    override fun initData() {
        super.initData()
        loadData()
        fragList = ArrayList()
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.scrollPivotX = 0.8f
        video_view_pager?.adapter = adapter
        val commonNavigatorAdapter: CommonNavigatorAdapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return alldata.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                // SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(alldata.get(index).title)
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#9e9e9e")
                simplePagerTitleView.selectedColor = Color.parseColor("#D43D3D")
                simplePagerTitleView.setOnClickListener { video_view_pager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 6.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#D43D3D"))
                return indicator
            }
        }
        commonNavigator.adapter = commonNavigatorAdapter
        magicindicator.navigator = commonNavigator
        commonNavigatorAdapter.notifyDataSetChanged()
        ViewPagerHelper.bind(magicindicator, video_view_pager)


        video_view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                Jzvd.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun loadData() {
        val open = appContext.resources.openRawResource(R.raw.video_list)
        val json = ConvertUtils.inputStream2String(open, "UTF-8")
        alldata = JSON.parseArray(json, MyChannel::class.java)
    }

    inner class MyHomeListViewPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            return VideoChildFragment.newInstance(alldata!![position])
        }

        override fun getCount(): Int {
            return alldata!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return alldata!![position].title
        }
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}