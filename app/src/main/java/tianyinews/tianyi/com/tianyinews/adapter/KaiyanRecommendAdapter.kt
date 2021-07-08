package tianyinews.tianyi.com.tianyinews.adapter

import android.graphics.Typeface
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.ItemKzHeaderHomeBinding
import tianyinews.tianyi.com.tianyinews.databinding.ItemKzHomeBinding
import tianyinews.tianyi.com.tianyinews.databinding.ItemKzOtherBinding

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 6:11 下午
 * @Description:
 */
class KaiyanRecommendAdapter  : BaseMultiItemQuickAdapter<VideItem, BaseDataBindingHolder<*>>() {

    init {
        addItemType(2, R.layout.item_kz_header_home)
        addItemType(3, R.layout.item_kz_home)
        addItemType(0, R.layout.item_kz_other)
    }

    override fun convert(holder: BaseDataBindingHolder<*>, item: VideItem) {
        when(holder.itemViewType){
            2->{
                var myholder  = holder.dataBinding as ItemKzHeaderHomeBinding
                myholder.tvTitle.typeface =  Typeface.createFromAsset(context?.assets, "fonts/Lobster-1.4.otf")
                myholder.videItem = item
                myholder.executePendingBindings()
            }
            3->{
                var myholder  = holder.dataBinding as ItemKzHomeBinding
                myholder.videItem = item
                myholder.executePendingBindings()
            }
            0->{
                var myholder  = holder.dataBinding as ItemKzOtherBinding
                myholder.executePendingBindings()
            }
        }
    }
}