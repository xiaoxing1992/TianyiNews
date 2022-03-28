package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.os.Trace
import androidx.fragment.app.Fragment
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.LogUtils
import dalvik.system.DexClassLoader
import okio.*
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.base.BaseFragment
import tianyinews.tianyi.com.tianyinews.databinding.ActivityMainBinding
import tianyinews.tianyi.com.tianyinews.fragment.CareFragment
import tianyinews.tianyi.com.tianyinews.fragment.HomeFragment
import tianyinews.tianyi.com.tianyinews.fragment.KaiyanFragment
import tianyinews.tianyi.com.tianyinews.fragment.VideoFragment
import tianyinews.tianyi.com.tianyinews.util.CacheUtil
import tianyinews.tianyi.com.tianyinews.util.SettingUtil
import tianyinews.tianyi.com.tianyinews.viewmodel.MainViewModel
import java.io.File

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private var currentFragment = R.id.buttom_home_rb_id
    private val tabsId = listOf(R.id.buttom_home_rb_id, R.id.buttom_kaiyan_rb_id, R.id.buttom_video_rb_id, R.id.buttom_care_rb_id)

    override fun layoutId(): Int {
        Trace.beginSection("onCreate()")
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.buttomHomeRbId.isChecked = true
        chooseFragment(R.id.buttom_home_rb_id)
        mDatabind.buttomKaiyanRbId.setOnClickListener {
            chooseFragment(R.id.buttom_kaiyan_rb_id)
        }
        mDatabind.buttomHomeRbId.setOnClickListener {
            chooseFragment(R.id.buttom_home_rb_id)
        }
        mDatabind.buttomVideoRbId.setOnClickListener {
            chooseFragment(R.id.buttom_video_rb_id)
        }

        mDatabind.buttomCareRbId.setOnClickListener {
            chooseFragment(R.id.buttom_care_rb_id)
        }


        Trace.endSection()

    }

    private fun chooseFragment(checkedId: Int) {
        currentFragment = checkedId

        val beginTransaction = supportFragmentManager.beginTransaction()

        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(checkedId.toString())
        if (fragment == null) {
            when (checkedId) {
                R.id.buttom_home_rb_id -> beginTransaction.add(R.id.main_fl, HomeFragment(), checkedId.toString())
                R.id.buttom_kaiyan_rb_id -> beginTransaction.add(R.id.main_fl, KaiyanFragment(), checkedId.toString())
                R.id.buttom_video_rb_id -> beginTransaction.add(R.id.main_fl, VideoFragment(), checkedId.toString())
                R.id.buttom_care_rb_id -> beginTransaction.add(R.id.main_fl, CareFragment(), checkedId.toString())
            }
        }

        tabsId.forEach { tab ->

            val aFragment = supportFragmentManager.findFragmentByTag(tab.toString()) as BaseFragment<*, *>?

            if (tab == checkedId) {
//                aFragment.currentFragment=aFragment::class.java
                aFragment?.let {
                    beginTransaction.show(it)
                }
            } else {
                aFragment?.let {
                    beginTransaction.hide(it)
                }
            }
        }

        beginTransaction.commit()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

}