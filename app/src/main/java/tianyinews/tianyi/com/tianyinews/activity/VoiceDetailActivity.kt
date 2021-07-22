package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.ActivityVoiceDetailBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.VoiceDetailViewModel

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/13 3:43 下午
 * @Description:
 */
class VoiceDetailActivity: BaseActivity<VoiceDetailViewModel, ActivityVoiceDetailBinding> (){

    override fun layoutId(): Int = R.layout.activity_voice_detail

    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, VoiceDetailActivity::class.java)
//                .putExtra()
            context.startActivity(starter)
        }
    }
}