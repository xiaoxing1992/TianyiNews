package tianyinews.tianyi.com.tianyinews.adapter

import android.graphics.Color
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

    companion object{
        const val TYPE_BANNER=1
        const val TYPE_HEADER=2
        const val TYPE_VIDEO=3
        const val TYPE_OTHER=0
    }

    init {
        addItemType(TYPE_HEADER, R.layout.item_kz_header_home)
        addItemType(TYPE_VIDEO, R.layout.item_kz_home)
        addItemType(TYPE_OTHER, R.layout.item_kz_other)
    }

    override fun convert(holder: BaseDataBindingHolder<*>, item: VideItem) {
        when(holder.itemViewType){
            TYPE_HEADER->{
                var myholder  = holder.dataBinding as ItemKzHeaderHomeBinding
                myholder.tvTitle.typeface =  Typeface.createFromAsset(context?.assets, "fonts/Lobster-1.4.otf")
                myholder.tvTitle.setTextColor(Color.BLACK)
                myholder.videItem = item
                myholder.executePendingBindings()
            }
            TYPE_VIDEO->{
                var myholder  = holder.dataBinding as ItemKzHomeBinding
                myholder.videItem = item
                myholder.executePendingBindings()
            }
            TYPE_OTHER->{
                var myholder  = holder.dataBinding as ItemKzOtherBinding
                myholder.executePendingBindings()
            }
        }
    }
}