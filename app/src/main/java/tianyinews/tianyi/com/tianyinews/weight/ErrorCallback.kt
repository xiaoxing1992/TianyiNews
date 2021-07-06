package tianyinews.tianyi.com.tianyinews.weight

import com.kingja.loadsir.callback.Callback
import tianyinews.tianyi.com.tianyinews.R

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/6 3:32 下午
 * @Description:
 */
class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}