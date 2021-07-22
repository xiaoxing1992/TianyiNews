package tianyinews.tianyi.com.tianyinews.gme

import android.content.Intent
import com.tencent.TMG.ITMGContext.ITMG_MAIN_EVENT_TYPE

/**
 * Created by baixs on 2017/11/20.
 */
interface TMGDispatcherBase {
    fun OnEvent(type: ITMG_MAIN_EVENT_TYPE?, data: Intent?)
}