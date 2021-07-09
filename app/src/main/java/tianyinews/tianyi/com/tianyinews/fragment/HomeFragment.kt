package tianyinews.tianyi.com.tianyinews.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.appContext
import com.trs.channellib.channel.channel.helper.ChannelDataHelepr
import com.trs.channellib.channel.channel.helper.ChannelDataHelepr.ChannelDataRefreshListenter
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
import tianyinews.tianyi.com.tianyinews.databinding.HomefragmentBinding
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.HomeChildFragment
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.HomeChildFragment.Companion.newInstance
import tianyinews.tianyi.com.tianyinews.util.GsonUtil
import tianyinews.tianyi.com.tianyinews.viewmodel.HomeVideModel
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */
class HomeFragment : BaseFragment<HomeVideModel, HomefragmentBinding>(), ChannelDataRefreshListenter {
    private val mDataList: MutableList<String> =  ArrayList()
    private var ids = 1
    private var IdsMap: MutableMap<String, Int> = HashMap()
    private var preIds: MutableList<String?> = ArrayList()
    private val adapter: MyHomeListViewPager by lazy { MyHomeListViewPager(childFragmentManager) }
    private val dataHelepr: ChannelDataHelepr<MyChannel> by lazy { ChannelDataHelepr<MyChannel>(requireContext(), this@HomeFragment, mDatabind.rlLl) }
    private var myChannels: MutableList<MyChannel> = mutableListOf()
    private var needShowPosition = -1
    private var isFirst = false

    override fun layoutId(): Int = R.layout.homefragment

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init()
        dataHelepr.setSwitchView(mDatabind.buttonMoreColumns)
        loadData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init()
        }
    }


    override fun onResume() {
        super.onResume()
        if(isFirst.not()){
            isFirst = true
            ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init()
        }
    }

    override fun initData() {
        super.initData()
        mDatabind.homeViewPager.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setPointer() {
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.scrollPivotX = 0.8f
        val commonNavigatorAdapter: CommonNavigatorAdapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList?.size ?: 0
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                // SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.text = mDataList!![index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#9e9e9e")
                simplePagerTitleView.selectedColor = Color.parseColor("#D43D3D")
                simplePagerTitleView.setOnClickListener { mDatabind.homeViewPager.currentItem = index }
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
        commonNavigatorAdapter.notifyDataSetChanged()
        mDatabind.mainMagicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(mDatabind.mainMagicIndicator, mDatabind.homeViewPager)
        adapter.notifyDataSetChanged()
        mDataList.clear()
    }

    override fun updateData() {
        loadData()
    }

    override fun onChannelSeleted(update: Boolean, posisiton: Int) {}
    private fun loadData() {
        val open = appContext.resources.openRawResource(R.raw.news_list)
        val json = ConvertUtils.inputStream2String(open, "UTF-8")
        val alldata = JSON.parseArray(json, MyChannel::class.java)
        val showChannels = dataHelepr.getShowChannels(alldata)
        myChannels.clear()
        myChannels.addAll(showChannels)
        adapter.notifyDataSetChanged()
        if (needShowPosition != -1) {
            mDatabind.homeViewPager.currentItem = needShowPosition
            needShowPosition = -1
        }
        for (i in myChannels.indices) {
            mDataList.add(myChannels[i].title)
        }
        setPointer()
    }

    internal inner class MyHomeListViewPager(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return newInstance(myChannels[position])
        }

        override fun getCount(): Int {
            return myChannels.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return myChannels[position].title
        }


        override fun getItemPosition(`object`: Any): Int {
            val fragment = `object` as HomeChildFragment
            val title = fragment.title
            val preId = preIds.indexOf(title)
            var newId = -1
            var i = 0
            val size = count
            while (i < size) {
                if (getPageTitle(i) == title) {
                    newId = i
                    break
                }
                i++
            }
            if (newId != -1 && newId == preId) {
                return POSITION_UNCHANGED
            }
            return if (newId != -1) {
                newId
            } else POSITION_NONE
        }

        override fun notifyDataSetChanged() {
            for (info in myChannels) {
                if (!IdsMap.containsKey(info.title)) {
                    IdsMap[info.title] = ids++
                }
            }
            super.notifyDataSetChanged()
            preIds.clear()
            val size = count
            for (i in 0 until size) {
                preIds.add(getPageTitle(i) as String?)
            }
        }
    }
}