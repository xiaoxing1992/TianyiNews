package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.activity.BaseVmDbActivity
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.ActivityRidevoiceCreateBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.RideVoiceCreateViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/13 11:33 上午
 * @Description:
 */
class RideVoiceCreateActivity : BaseActivity<RideVoiceCreateViewModel, ActivityRidevoiceCreateBinding>() {

    override fun layoutId(): Int = R.layout.activity_ridevoice_create

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init()
        mDatabind.tvCreate.isEnabled = false
        KeyboardUtils.showSoftInput(mDatabind.etCode)
    }

    override fun initListener() {
        mDatabind.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RideVoiceCreateActivity::class.java)
//                .putExtra()
            context.startActivity(starter)
        }
    }
}