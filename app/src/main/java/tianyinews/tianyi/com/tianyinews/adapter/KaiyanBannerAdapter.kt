package tianyinews.tianyi.com.tianyinews.adapter

import androidx.databinding.DataBindingUtil
import coil.load
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.ItemKyHomeBannerBinding

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/8 4:02 下午
 * @Description:
 */
class KaiyanBannerAdapter(val mRoundCorner: Int) : BaseBannerAdapter<VideItem>() {
    override fun bindData(holder: BaseViewHolder<VideItem>, data: VideItem, position: Int, pageSize: Int) {
        val dataBinding : ItemKyHomeBannerBinding? = DataBindingUtil.bind(holder.itemView)
        if(dataBinding!=null){
            dataBinding.bannerData = data
        }
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_ky_home_banner
}