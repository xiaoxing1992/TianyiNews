package tianyinews.tianyi.com.tianyinews.adapter

import android.graphics.Color
import android.graphics.Typeface
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.xk.eyepetizer.mvp.model.bean.KzCategory
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.databinding.*

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/8 5:31 下午
 * @Description:
 */
class KyCategoryAdapter : BaseMultiItemQuickAdapter<KzCategory, BaseDataBindingHolder<*>>() {

    companion object {
        const val TYPE_END = 0
        const val TYPE_CONTENT = 1
    }

    init {
        addItemType(TYPE_END, R.layout.item_ky_category_end)
        addItemType(TYPE_CONTENT, R.layout.item_ky_category)
    }

    override fun convert(holder: BaseDataBindingHolder<*>, item: KzCategory) {
        when (holder.itemViewType) {
            TYPE_END -> {
                var myholder = holder.dataBinding as ItemKyCategoryEndBinding
                myholder.tvTextEnd.typeface =  Typeface.createFromAsset(context?.assets, "fonts/Lobster-1.4.otf")
                myholder.tvTextEnd.setTextColor(Color.BLACK)
                myholder.executePendingBindings()
            }
            TYPE_CONTENT -> {
                var myholder = holder.dataBinding as ItemKyCategoryBinding
                myholder.categoryItem = item
                myholder.executePendingBindings()
            }
        }
    }
}