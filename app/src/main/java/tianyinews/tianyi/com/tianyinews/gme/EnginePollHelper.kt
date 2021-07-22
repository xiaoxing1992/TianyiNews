package tianyinews.tianyi.com.tianyinews.gme

import android.os.Handler
import com.tencent.TMG.ITMGContext

class EnginePollHelper internal constructor() {
    private val mhandler = Handler()
    private val mRunnable: Runnable = object : Runnable {
        override fun run() {
            if (s_pollEnabled) {
                if (ITMGContext.GetInstance(null) != null) ITMGContext.GetInstance(null).Poll()
            }
            mhandler.postDelayed(this, 33)
        }
    }

    private fun startTimer() {
        mhandler.postDelayed(mRunnable, 33)
    }

    private fun stopTimer() {
        mhandler.removeCallbacks(mRunnable)
    }


    companion object {
        private var s_enginePollHelper: EnginePollHelper? = null
        private var s_pollEnabled = true
        fun createEnginePollHelper() {
            if (s_enginePollHelper == null) {
                s_enginePollHelper = EnginePollHelper()
                s_enginePollHelper!!.startTimer()
            }
        }

        fun destroyEnginePollHelper() {
            s_enginePollHelper?.stopTimer()
            s_enginePollHelper = null
        }

        fun pauseEnginePollHelper() {
            s_pollEnabled = false
        }

        fun resumeEnginePollHelper() {
            s_pollEnabled = true
        }
    }
}