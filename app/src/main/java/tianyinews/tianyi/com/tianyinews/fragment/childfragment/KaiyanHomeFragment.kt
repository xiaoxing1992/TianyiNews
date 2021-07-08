package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kingja.loadsir.core.LoadService
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.homechildfragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanBannerAdapter
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanHomeAdapter
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanRecommendAdapter
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKaiyanBinding
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKzHomeBinding
import tianyinews.tianyi.com.tianyinews.ext.loadServiceInit
import tianyinews.tianyi.com.tianyinews.ext.showLoading
import tianyinews.tianyi.com.tianyinews.viewmodel.KzHomeViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 5:55 下午
 * @Description:
 */
class KaiyanHomeFragment : BaseFragment<KzHomeViewModel, FragmentKzHomeBinding>() {

    private val mAdapter: KaiyanRecommendAdapter by lazy { KaiyanRecommendAdapter() }
    private val headerView: View by lazy { LayoutInflater.from(requireContext()).inflate(R.layout.header_message_update, null) }
    private val tv_title: TextView by lazy { headerView.findViewById(R.id.tv_title) }

    //界面状态管理者
    private val loadsir: LoadService<Any> by lazy {
        loadServiceInit(mDatabind.refreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.requestData()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_kz_home

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.recyclerView.adapter = mAdapter
        if (mAdapter.headerLayoutCount > 0) return
        mAdapter.addHeaderView(createHeadView())
    }

    private lateinit var mViewPager: BannerViewPager<VideItem>

    private fun createHeadView(): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.header_kaiyan_home_banner, null)
        mViewPager = view.findViewById(R.id.banner_view)
        mViewPager.apply {
            setLifecycleRegistry(lifecycle)
            adapter = KaiyanBannerAdapter(ConvertUtils.dp2px(8f))
            setIndicatorGravity(IndicatorGravity.END)
            setIndicatorMargin(0,0,ConvertUtils.dp2px(32f),ConvertUtils.dp2px(10f))
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setIndicatorSlideMode(IndicatorSlideMode.WORM)
            setIndicatorSliderColor(
                resources.getColor(R.color.red_normal_color),
                resources.getColor(R.color.red_checked_color)
            )
            setIndicatorSliderRadius(
                ConvertUtils.dp2px(5f),
                ConvertUtils.dp2px(5f)
            )
            setPageMargin(ConvertUtils.dp2px(15f))
            setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            setScrollDuration(800)
            setRevealWidth(ConvertUtils.dp2px(10f), ConvertUtils.dp2px(10f))
//            setOnPageClickListener { _: View, position: Int -> itemClick(position) }
            setInterval(3000)
            create()
        }
        return view
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        //获取网络数据
        mViewModel?.requestData()
    }

    override fun initListener() {
        mDatabind.refreshLayout.setOnRefreshListener {
            //设置界面 加载中
            loadsir.showLoading()
            //获取网络数据
            mViewModel?.requestData()
        }
        mDatabind.refreshLayout.setOnLoadMoreListener {
            //获取网络数据
            mViewModel?.requestMoreData()
        }
    }

    override fun createObserver() {
        mViewModel.run {
            bannerDataState.observe(viewLifecycleOwner, Observer {
                mViewPager.refreshData(it)
            })
            homeDataState.observe(viewLifecycleOwner, Observer {
                if (it.isRefresh) {
                    mAdapter.setList(it.listData)
                    loadsir.showSuccess()
                    refreshLayout!!.finishRefresh()
//                    mAdapter.addHeaderView(headerView)
//                    tv_title.text = "今日头条推荐引擎有${it.listData?.size}条更新"
//                    tv_title.postDelayed({
//                        mAdapter.removeHeaderView(headerView)
//                    }, 3000)
                } else {
                    it.listData?.let {
                        mAdapter.addData(it)
                        loadsir.showSuccess()
                    }

                    mDatabind.refreshLayout.finishLoadMore()
                }

            })

            moreDataState.observe(viewLifecycleOwner, Observer {
                it.listData?.let {
                    mAdapter.addData(it)
                }

                mDatabind.refreshLayout.finishLoadMore()
            })
        }
    }

    companion object {
        const val MODEL_KEY = "model_key"

        @JvmStatic
        fun newInstance(): KaiyanHomeFragment {
            val fragment = KaiyanHomeFragment()
            return fragment
        }
    }
}