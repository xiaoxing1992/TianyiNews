package tianyinews.tianyi.com.tianyinews.gme

import android.content.Intent
import com.tencent.TMG.ITMGContext.ITMGDelegate
import com.tencent.TMG.ITMGContext.ITMG_MAIN_EVENT_TYPE
import tianyinews.tianyi.com.tianyinews.delegate.GMEDelegate
import java.util.*

class TMGCallbackDispatcher private constructor() : ITMGDelegate() {
    private val mSubDispatcherSet: MutableSet<TMGDispatcherBase>
    override fun OnEvent(type: ITMG_MAIN_EVENT_TYPE, data: Intent) {
        for (subDispatcher in mSubDispatcherSet) {
            subDispatcher.OnEvent(type, data)
        }
    }

    fun registerSubDispatcher(subDispatcher: TMGDispatcherBase) {
        mSubDispatcherSet.add(subDispatcher)
    }

    fun unregisterSubDispatcher(subDispatcher: TMGDispatcherBase) {
        mSubDispatcherSet.remove(subDispatcher)
    }

    companion object {
        @Volatile
        private var mInstance: TMGCallbackDispatcher? = null

        @JvmStatic
        fun getInstance(): TMGCallbackDispatcher {
            return mInstance ?: synchronized(this) {
                mInstance ?: TMGCallbackDispatcher().also { mInstance = it }
            }
        }
    }

    init {
        mSubDispatcherSet = HashSet()
    }
}