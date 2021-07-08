package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.homechildfragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.activity.WebActivity
import tianyinews.tianyi.com.tianyinews.adapter.MyVideoListViewAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.databinding.HomechildfragmentBinding
import tianyinews.tianyi.com.tianyinews.ext.loadServiceInit
import tianyinews.tianyi.com.tianyinews.ext.showLoading
import tianyinews.tianyi.com.tianyinews.viewmodel.VideoChildViewModel

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */
class VideoChildFragment : BaseFragment<VideoChildViewModel, HomechildfragmentBinding>() {

    private val headerView: View by lazy { LayoutInflater.from(requireContext()).inflate(R.layout.header_message_update, null) }
    private val tv_title: TextView by lazy { headerView.findViewById(R.id.tv_title) }
    private val adapter: MyVideoListViewAdapter by lazy { MyVideoListViewAdapter() }
    private val mChannel: MyChannel? by lazy { arguments?.getSerializable(KEY_URL) as MyChannel }

    //界面状态管理者
    private val loadsir: LoadService<Any> by lazy {
        loadServiceInit(mDatabind.refreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.reuqestData(true, mChannel?.type!!)
        }
    }

    override fun layoutId(): Int = R.layout.homechildfragment

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.recyclerView.adapter = adapter
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //设置界面 加载中
        loadsir.showLoading()
        //获取网络数据
        mViewModel.reuqestData(true, mChannel?.type!!)
    }

    override fun initListener() {
        super.initListener()
        mDatabind.refreshLayout.setOnRefreshListener(OnRefreshListener {
            //设置界面 加载中
            loadsir.showLoading()
            //获取网络数据
            mViewModel.reuqestData(true, mChannel?.type!!)
        })
        mDatabind.refreshLayout.setOnLoadMoreListener(OnLoadMoreListener {
            //设置界面 加载中
            loadsir.showLoading()
            //获取网络数据
            mViewModel.reuqestData(false, mChannel?.type!!)
        })

        adapter?.setOnItemClickListener { q, view, position ->
            val item = adapter.getItemOrNull(position)?:return@setOnItemClickListener
            WebActivity.start(requireContext(),item.share_url)
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
        }
    }


    companion object {
        const val KEY_URL = "key_url"

        @JvmStatic
        fun newInstance(channel: MyChannel): VideoChildFragment {
            val args = Bundle()
            args.putSerializable(KEY_URL, channel)
            val fragment = VideoChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}