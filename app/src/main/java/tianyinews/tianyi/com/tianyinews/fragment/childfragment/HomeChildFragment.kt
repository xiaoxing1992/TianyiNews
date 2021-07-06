package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.homechildfragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.activity.WebActivity
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.databinding.HomechildfragmentBinding
import tianyinews.tianyi.com.tianyinews.ext.loadServiceInit
import tianyinews.tianyi.com.tianyinews.ext.showLoading
import tianyinews.tianyi.com.tianyinews.ext.showMessage
import tianyinews.tianyi.com.tianyinews.viewmodel.HomeChildViewModel

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */
class HomeChildFragment : BaseFragment<HomeChildViewModel, HomechildfragmentBinding>() {

    private val adapter: MyHomeListViewAdapter by lazy { MyHomeListViewAdapter() }
    private var mChannel: MyChannel? = null
    private val headerView: View by lazy { LayoutInflater.from(requireContext()).inflate(R.layout.header_message_update, null) }
    private val tv_title: TextView by lazy { headerView.findViewById(R.id.tv_title) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId(): Int = R.layout.homechildfragment

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(mDatabind.refreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.reuqestData(true, mChannel!!.requestTitle)
        }


        val bundle = arguments
        mChannel = bundle!!.getSerializable(MODEL_KEY) as MyChannel?
        mDatabind.recyclerView.adapter = adapter
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        //获取网络数据
        mViewModel.reuqestData(true, mChannel!!.requestTitle)
    }

    override fun initData() {

        mDatabind.refreshLayout?.setOnRefreshListener {
            mViewModel.reuqestData(true, mChannel!!.requestTitle)
        }
        mDatabind.refreshLayout?.setOnLoadMoreListener {
            mViewModel.reuqestData(false, mChannel!!.requestTitle)
        }
        adapter.setOnItemClickListener { ada: BaseQuickAdapter<*, *>, view: View?, position: Int ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            val newUrl = item.url
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("newUrl", newUrl)
            startActivity(intent)
        }
        adapter.setOnItemLongClickListener { adapter: BaseQuickAdapter<*, *>, view: View?, position: Int ->
            showMessage("确定收藏吗？收藏以后更方便阅读精彩内容", "收藏精彩看点", positiveAction = {
                //收藏状态是在已经登录的情况下才能收藏
                //收藏到数据库并将关注页面状态更改
            }, negativeAction = {
                dismissLoading()
            })
            false
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

    val title: String
        get() = mChannel!!.title

    companion object {
        const val MODEL_KEY = "model_key"

        @JvmStatic
        fun newInstance(channel: MyChannel): HomeChildFragment {
            val args = Bundle()
            args.putSerializable(MODEL_KEY, channel)
            val fragment = HomeChildFragment()
            fragment.arguments = args
            return fragment
        }
    }


}