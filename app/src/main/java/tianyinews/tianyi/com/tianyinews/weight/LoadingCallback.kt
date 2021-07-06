package tianyinews.tianyi.com.tianyinews.weight

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import tianyinews.tianyi.com.tianyinews.R

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 3:03 下午
 * @Description:
 */
class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}