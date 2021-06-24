package tianyinews.tianyi.com.tianyinews.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.jzvd.Jzvd
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.videofragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.VideoChildFragment
import tianyinews.tianyi.com.tianyinews.util.GsonUtil
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */
class VideoFragment : BaseFragment() {
    private var fragList: ArrayList<BaseFragment>? = null
    var adapter: MyHomeListViewPager? = null
    private var alldata: List<MyChannel>? = null
    override fun initView(): View {
        val view = View.inflate(mContext, R.layout.videofragment, null)
        ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayBackground).statusBarDarkFont(true).init()
        alldata = ArrayList()
        return view
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
        adapter = MyHomeListViewPager(activity!!.supportFragmentManager)
        video_view_pager?.adapter = adapter
        adapter?.notifyDataSetChanged()
        video_tab_layout?.tabMode = TabLayout.MODE_SCROLLABLE
        for (i in alldata?.indices!!) {
            video_tab_layout?.addTab(video_tab_layout!!.newTab().setText(alldata!![i].title))
        }
        val adapter: MyHomeListViewPager = MyHomeListViewPager(
            activity!!.supportFragmentManager
        )
        video_view_pager?.adapter = adapter
        video_tab_layout?.setupWithViewPager(video_view_pager)
        video_tab_layout?.setTabsFromPagerAdapter(adapter)
        video_view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                Jzvd.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun loadData() {
        val data = fromRaw
        alldata = GsonUtil.jsonToBeanList(data, MyChannel::class.java)
    }

    private val fromRaw: String
        private get() {
            val result = ""
            try {
                val input = resources.openRawResource(R.raw.video_list)
                val output = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var length = 0
                while (input.read(buffer).also { length = it } != -1) {
                    output.write(buffer, 0, length)
                }
                output.close()
                input.close()
                return output.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

    inner class MyHomeListViewPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            val videoChildFragment = VideoChildFragment()
            val bundle = Bundle()
            //      bundle.putString(HomeChildFragment.KEY_TITLE, alldata.get(position));
            bundle.putString(VideoChildFragment.KEY_URL, alldata!![position].url)
            bundle.putString(VideoChildFragment.KEY_URL_FOOTER, alldata!![position].url_footer)
            videoChildFragment.arguments = bundle
            return videoChildFragment
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