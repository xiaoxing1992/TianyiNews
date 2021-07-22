package tianyinews.tianyi.com.tianyinews.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import cn.jzvd.JzvdStd
import tianyinews.tianyi.com.tianyinews.R

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/12 6:29 下午
 * @Description:
 */
class JzvdStdTikTok(context: Context?, attrs: AttributeSet?) : JzvdStd(context, attrs) {

    override fun init(context: Context?) {
        super.init(context)
        bottomContainer.visibility = GONE;
        topContainer.visibility = GONE;
        bottomProgressBar.visibility = GONE;
        posterImageView.scaleType = ImageView.ScaleType.FIT_CENTER;
    }

    //changeUiTo 真能能修改ui的方法
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        bottomContainer.visibility = GONE;
        topContainer.visibility = GONE;
    }

    override fun setAllControlsVisiblity(topCon: Int, bottomCon: Int, startBtn: Int, loadingPro: Int, posterImg: Int, bottomPro: Int, retryLayout: Int) {
        topContainer.visibility = INVISIBLE;
        bottomContainer.visibility = INVISIBLE;
        startButton.visibility = startBtn;
        loadingProgressBar.visibility = loadingPro;
        posterImageView.visibility = posterImg;
        bottomProgressBar.visibility = GONE;
        mRetryLayout.visibility = retryLayout;
    }

    override fun dissmissControlView() {
        if (state != STATE_NORMAL && state != STATE_ERROR && state != STATE_AUTO_COMPLETE) {
            post {
                bottomContainer.visibility = View.INVISIBLE
                topContainer.visibility = View.INVISIBLE
                startButton.visibility = View.INVISIBLE
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss()
                }
                if (screen != SCREEN_TINY) {
                    bottomProgressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onClickUiToggle() {
        super.onClickUiToggle()
        Log.i(TAG, "click blank");
        startButton.performClick();
        bottomContainer.visibility = GONE;
        topContainer.visibility = GONE;
    }

    override fun updateStartImage() {
        if (state == STATE_PLAYING) {
            startButton.visibility = VISIBLE
            startButton.setImageResource(R.mipmap.tiktok_play_tiktok)
            replayTextView.visibility = GONE
        } else if (state == STATE_ERROR) {
            startButton.visibility = INVISIBLE
            replayTextView.visibility = GONE
        } else if (state == STATE_AUTO_COMPLETE) {
            startButton.visibility = VISIBLE
            startButton.setImageResource(R.mipmap.tiktok_play_tiktok)
            replayTextView.visibility = VISIBLE
        } else {
            startButton.setImageResource(R.mipmap.tiktok_play_tiktok)
            replayTextView.visibility = GONE
        }
    }
}