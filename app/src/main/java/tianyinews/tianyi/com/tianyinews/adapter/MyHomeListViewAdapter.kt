package tianyinews.tianyi.com.tianyinews.adapter

import android.view.View
import android.widget.*
import coil.load
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.NewsDataModel
import tianyinews.tianyi.com.tianyinews.bean.NewsMultiItemEntity

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */
class MyHomeListViewAdapter : BaseMultiItemQuickAdapter<NewsDataModel, BaseViewHolder>() {
    override fun convert(helper: BaseViewHolder, item: NewsDataModel) {
        // 根据返回的 type 分别设置数据
        when (helper.itemViewType) {
            NewsMultiItemEntity.IMG -> {
                helper.setText(R.id.type_one_title, item.title)
                helper.setText(R.id.type_one_groupname, item.author_name)
                helper.setText(R.id.type_one_comments, item.category)
            }
            NewsMultiItemEntity.IMG_TWO -> {
                helper.setText(R.id.type_one_two_title, item.title)
                helper.setText(R.id.type_one_two_groupname, item.author_name)
                helper.setText(R.id.type_one_two_comments, item.category)
                helper.getView<ImageView>(R.id.type_one_two_img).load(item.thumbnail_pic_s)
            }
            NewsMultiItemEntity.IMG_THREE -> {
                helper.setText(R.id.type_one_three_title, item.title)
                helper.setText(R.id.type_one_three_groupname, item.author_name)
                helper.setText(R.id.type_one_three_comments, item.category)
                helper.getView<ImageView>(R.id.type_one_three_img).load(item.thumbnail_pic_s)
                helper.getView<ImageView>(R.id.type_one_three_img02).load(item.thumbnail_pic_s02)
                helper.getView<ImageView>(R.id.type_one_three_img03).load(item.thumbnail_pic_s03)
            }
            NewsMultiItemEntity.AD -> {
                val adview = helper.getView<AdView>(R.id.ad_view)
                adview.loadAd(AdRequest.Builder().build())
            }
            else -> {
                helper.setText(R.id.type_one_title, item.title)
                helper.setText(R.id.type_one_groupname, item.author_name)
                helper.setText(R.id.type_one_comments, item.category)
            }
        }
    }

    companion object {
        private const val TAG = "MyHomeListViewAdapter"
    }

    init {
        addItemType(NewsMultiItemEntity.TEXT, R.layout.hometype_one)
        addItemType(NewsMultiItemEntity.IMG, R.layout.hometype_one)
        addItemType(NewsMultiItemEntity.IMG_TWO, R.layout.hometype_one_two)
        addItemType(NewsMultiItemEntity.IMG_THREE, R.layout.hometype_one_three)
        addItemType(NewsMultiItemEntity.AD, R.layout.hometype_ad)
    }
}