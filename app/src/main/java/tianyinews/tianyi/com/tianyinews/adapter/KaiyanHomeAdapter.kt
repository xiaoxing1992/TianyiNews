package tianyinews.tianyi.com.tianyinews.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.ItemKzHomeBinding

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/7 6:11 下午
 * @Description:
 */
class KaiyanHomeAdapter  : BaseQuickAdapter<VideItem, BaseDataBindingHolder<ItemKzHomeBinding>>(R.layout.item_kz_home) {

    companion object {
        private const val TAG = "MyHomeListViewAdapter"
    }

    override fun convert(holder: BaseDataBindingHolder<ItemKzHomeBinding>, item: VideItem) {
        var binding = holder.dataBinding
        if(binding!=null){
            binding.videItem = item
            binding.executePendingBindings()
        }
    }
}