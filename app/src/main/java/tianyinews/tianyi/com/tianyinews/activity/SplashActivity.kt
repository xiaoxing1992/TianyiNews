package tianyinews.tianyi.com.tianyinews.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.rz.commonlibrary.base.activity.BaseVmDbActivity
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.SplashLayoutBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.SplashViewModel

/**
 * Created by Administrator on 2017/3/10.
 */
class SplashActivity : BaseActivity<SplashViewModel,SplashLayoutBinding>() {

    private val alphaAnimation: AlphaAnimation by lazy { AlphaAnimation(0.3f, 1f).apply {
        duration = 3000
    } }

    override fun layoutId(): Int = R.layout.splash_layout

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).transparentStatusBar().init()

        mDatabind.root.animation = alphaAnimation
        mDatabind.root.startAnimation(alphaAnimation)

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                MainActivity.start(this@SplashActivity)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
}