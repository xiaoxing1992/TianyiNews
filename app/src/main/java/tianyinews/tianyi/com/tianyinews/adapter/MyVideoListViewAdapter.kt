package tianyinews.tianyi.com.tianyinews.adapter

import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.VideoBean

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */
class MyVideoListViewAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.videochildfragment) {
    override fun convert(helper: BaseViewHolder, videoBean: VideoBean) {
        helper.setText(R.id.tv_video_topicname, videoBean.topicName)
        helper.setText(R.id.tv_video_ptime, videoBean.ptime)
        val jzvdStd = helper.getView<JzvdStd>(R.id.player_list_video)
        jzvdStd.setUp(videoBean.mp4_url, videoBean.title, Jzvd.SCREEN_NORMAL)
        jzvdStd.posterImageView.load(videoBean.cover)
    }

    companion object {
        private const val TAG = "MyHomeListViewAdapter"
    }
}