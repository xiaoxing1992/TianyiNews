package tianyinews.tianyi.com.tianyinews.adapter

import android.graphics.Color
import android.graphics.Typeface
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 5:46 下午
 * @Description:
 */
class KaiyanHotChildAdapter  : BaseMultiItemQuickAdapter<VideItem, BaseDataBindingHolder<*>>() {

    init {
        addItemType(KaiyanRecommendAdapter.TYPE_END, R.layout.item_ky_category_end)
        addItemType(KaiyanRecommendAdapter.TYPE_VIDEO, R.layout.item_ky_category_hot)
    }

    override fun convert(holder: BaseDataBindingHolder<*>, item: VideItem) {
        when (holder.itemViewType) {
            KaiyanRecommendAdapter.TYPE_END -> {
                var myholder = holder.dataBinding as ItemKyCategoryEndBinding
                myholder.tvTextEnd.typeface =  Typeface.createFromAsset(context?.assets, "fonts/Lobster-1.4.otf")
                myholder.tvTextEnd.setTextColor(Color.BLACK)
                myholder.executePendingBindings()
            }
            KaiyanRecommendAdapter.TYPE_VIDEO -> {
                var myholder = holder.dataBinding as ItemKyCategoryHotBinding
                myholder.videoItem = item
                myholder.executePendingBindings()
            }
        }
    }
}