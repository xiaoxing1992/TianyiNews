package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.bean.VideItem
import tianyinews.tianyi.com.tianyinews.databinding.ActivityVideoDetailBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.VideoDetailViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/12 5:55 下午
 * @Description:
 */
class VideoDetailActivity : BaseActivity<VideoDetailViewModel, ActivityVideoDetailBinding>() {

    private var itemData: VideItem? = null

    override fun layoutId(): Int = R.layout.activity_video_detail

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarDarkFont(false).statusBarColor(R.color.left_drawer_item_text_normal).init()


        itemData = intent.getSerializableExtra("item") as VideItem
        if (itemData == null) finish()
        mDatabind.videoView.isRotateViewAuto = false

        itemData!!.data?.playInfo?.forEach {
            if (it.type == "high") {
                val playUrl = it.url
                mDatabind.videoView.setUp(playUrl, false, "")
                mDatabind.videoView.startPlayLogic()
                return@forEach
            }
        }

        mDatabind.videoView.fullscreenButton.setOnClickListener {

            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            }

            mDatabind.videoView.startWindowFullscreen(this, true, true)

        }
        mDatabind.videoView.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDatabind.videoView?.fullWindowPlayer?.fullscreenButton?.setOnClickListener {
            GSYVideoManager.backFromWindowFull(this)
            if (this.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            }

        }
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            if (this.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            }
            return
        }
        super.onBackPressed()
    }

    companion object {
        @JvmStatic
        fun start(context: Context, item: VideItem) {
            val starter = Intent(context, VideoDetailActivity::class.java)
            starter.putExtra("item", item)
            context.startActivity(starter)
        }
    }
}