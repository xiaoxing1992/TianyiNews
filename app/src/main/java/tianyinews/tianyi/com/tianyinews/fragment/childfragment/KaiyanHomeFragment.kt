package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.homechildfragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanHomeAdapter
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanRecommendAdapter
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
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

    private val adapter: KaiyanRecommendAdapter by lazy { KaiyanRecommendAdapter() }
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
        mDatabind.recyclerView.adapter = adapter
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
            homeDataState.observe(viewLifecycleOwner, Observer {
                if (it.isRefresh) {
                    adapter.setList(it.listData)
                    loadsir.showSuccess()
                    refreshLayout!!.finishRefresh()
                    if (adapter.hasHeaderLayout()) {
                        adapter.removeAllHeaderView()
                    }
                    adapter.addHeaderView(headerView)
                    tv_title.text = "今日头条推荐引擎有${it.listData?.size}条更新"
                    tv_title.postDelayed({
                        adapter.removeHeaderView(headerView)
                    }, 3000)
                } else {
                    it.listData?.let {
                        adapter.addData(it)
                        loadsir.showSuccess()
                    }

                    mDatabind.refreshLayout.finishLoadMore()
                }

            })

            moreDataState.observe(viewLifecycleOwner, Observer {
                it.listData?.let {
                    adapter.addData(it)
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