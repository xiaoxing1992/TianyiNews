package tianyinews.tianyi.com.tianyinews.util

import android.view.View

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 7:02 下午
 * @Description:
 */
object TimeUtil {
    @JvmStatic
    fun durationFormat(duration: Long?): String {
        val minute = duration!! / 60
        val second = duration % 60
        if (minute <= 9) {
            if (second <= 9) {
                return "0${minute}' 0${second}''"
            } else {
                return "0${minute}' ${second}''"
            }
        } else {
            if (second <= 9) {
                return "${minute}' 0${second}''"
            } else {
                return "${minute}' ${second}''"
            }
        }
    }
}