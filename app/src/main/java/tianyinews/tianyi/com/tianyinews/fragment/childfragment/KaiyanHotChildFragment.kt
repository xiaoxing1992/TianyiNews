package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import kotlinx.android.synthetic.main.homechildfragment.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.activity.VideoDetailActivity
import tianyinews.tianyi.com.tianyinews.adapter.KaiyanHotChildAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKaiyanHotChildBinding
import tianyinews.tianyi.com.tianyinews.ext.loadServiceInit
import tianyinews.tianyi.com.tianyinews.ext.showLoading
import tianyinews.tianyi.com.tianyinews.viewmodel.KaiyanHotChildViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 3:51 下午
 * @Description:
 */
class KaiyanHotChildFragment : BaseFragment<KaiyanHotChildViewModel, FragmentKaiyanHotChildBinding>() {

    private val type by lazy { arguments?.getString("type") ?: "" }

    //界面状态管理者
    private val loadsir: LoadService<Any> by lazy {
        loadServiceInit(mDatabind.refreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.reuqestData(type)
        }
    }

    private val mAdapter: KaiyanHotChildAdapter by lazy { KaiyanHotChildAdapter() }

    override fun layoutId(): Int = R.layout.fragment_kaiyan_hot_child

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.recyclerView.adapter = mAdapter
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //点击重试时触发的操作
        loadsir.showLoading()
        mViewModel.reuqestData(type)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.run {
            liveData.observe(viewLifecycleOwner, Observer {
                loadsir.showSuccess()
                refreshLayout!!.finishRefresh()
                val toMutableList = it.listData?.toMutableList()
                toMutableList?.add(VideItem("2002", null, "2002"))
                mAdapter.setList(toMutableList)
            })
        }
    }

    override fun initListener() {
        super.initListener()
        mDatabind.refreshLayout.setOnRefreshListener {
            mViewModel.reuqestData(type)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.getItemOrNull(position) ?: return@setOnItemClickListener
            VideoDetailActivity.start(requireContext(),item)
        }
    }

    companion object {
        fun newInstance(type: String): KaiyanHotChildFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = KaiyanHotChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}