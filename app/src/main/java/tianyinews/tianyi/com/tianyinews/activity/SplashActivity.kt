package tianyinews.tianyi.com.tianyinews.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.databinding.SplashLayoutBinding

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