package tianyinews.tianyi.com.tianyinews.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.activity.BaseVmDbActivity
import kotlinx.coroutines.delay
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.SplashLayoutBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.SplashViewModel

/**
 * Created by Administrator on 2017/3/10.
 */
class SplashActivity : AppCompatActivity(R.layout.splash_layout) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = SplashLayoutBinding.inflate(layoutInflater)

        lifecycleScope.launchWhenResumed {
            delay(300L)
            MainActivity.start(this@SplashActivity)
            finish()
        }
    }
}