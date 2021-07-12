package tianyinews.tianyi.com.tianyinews.fragment.childfragment

import android.graphics.Rect
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.activity.VideoDetailActivity
import tianyinews.tianyi.com.tianyinews.adapter.KyCategoryAdapter
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKzCategoryBinding
import tianyinews.tianyi.com.tianyinews.databinding.FragmentKzHomeBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.KzCategoryViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/8 5:19 下午
 * @Description:
 */
class KaiyanCategoryFragment : BaseFragment<KzCategoryViewModel, FragmentKzCategoryBinding>() {

    private val gridLayoutManager by lazy { GridLayoutManager(requireContext(), 2)}
    private val mAdapter by lazy { KyCategoryAdapter() }

    override fun layoutId(): Int = R.layout.fragment_kz_category

    override fun initView(savedInstanceState: Bundle?) {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (gridLayoutManager.itemCount - 1 == position) {
                    return 2
                }
                return 1
            }

        }
        mDatabind.rvCategory.layoutManager = gridLayoutManager
        mDatabind.rvCategory.adapter = mAdapter
        mDatabind.rvCategory.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildPosition(view)
                val offset = SizeUtils.dp2px(2f)

                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                    if (position % 2 == 0) offset else 0, offset)
            }

        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.requestData()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.run {
            liveData.observe(viewLifecycleOwner, Observer {
                val toMutableList = it.listData?.toMutableList()
                toMutableList?.add(KzCategory(2002))
                mAdapter.setList(toMutableList)
            })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): KaiyanCategoryFragment {
            val fragment = KaiyanCategoryFragment()
            return fragment
        }
    }
}