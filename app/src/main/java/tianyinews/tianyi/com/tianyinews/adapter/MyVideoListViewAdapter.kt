package tianyinews.tianyi.com.tianyinews.adapter

import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideoBean
import tianyinews.tianyi.com.tianyinews.bean.VideoResultModel

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */
class MyVideoListViewAdapter : BaseQuickAdapter<VideoResultModel, BaseViewHolder>(R.layout.videochildfragment) {
    override fun convert(helper: BaseViewHolder, item: VideoResultModel) {
        helper.setText(R.id.tv_video_topicname, item.title)
        helper.setText(R.id.tv_video_ptime, item.author)
//        val jzvdStd = helper.getView<JzvdStd>(R.id.player_list_video)
//        jzvdStd.setUp(item.share_url, item.title, Jzvd.SCREEN_NORMAL)
//        jzvdStd.posterImageView.load(item.item_cover)
    }

    companion object {
        private const val TAG = "MyHomeListViewAdapter"
    }
}