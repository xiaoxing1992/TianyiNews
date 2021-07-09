package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import cn.jzvd.Jzvd
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.shareboard.SnsPlatform
import thinkfreely.changemodelibrary.ChangeModeController
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.fragment.CareOldFragment
import tianyinews.tianyi.com.tianyinews.fragment.HomeFragment
import tianyinews.tianyi.com.tianyinews.fragment.KaiyanFragment
import tianyinews.tianyi.com.tianyinews.fragment.VideoOldFragment
import tianyinews.tianyi.com.tianyinews.util.ConnUtil
import java.util.*

class MainActivity : AppCompatActivity(){
    private var main_rb: RadioGroup? = null
    private var manager: FragmentManager? = null
    private var homeFragment: HomeFragment? = null
    private var kyFragment: KaiyanFragment? = null
    private var videoFragment: VideoOldFragment? = null
    var platforms = ArrayList<SnsPlatform>()
    private val list = arrayOf(SHARE_MEDIA.QQ)
    private var careFragment: CareOldFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ChangeModeController.getInstance().init(this@MainActivity,R.attr::class.java)
        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme)
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        //进入主界面判断网络状态
        val netWorkAvailable = ConnUtil.isNetWorkAvailable(this)
        if (!netWorkAvailable) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("网络未连接，是否开启网络连接？")
            builder.setPositiveButton("确定") { dialogInterface, i ->
                val items = arrayOf("WiFi", "移动数据")
                val isWifi = ConnUtil.isWiFi(this@MainActivity)
                val isMobile = ConnUtil.isMobile(this@MainActivity)
                if (!isWifi || !isMobile) {
                    AlertDialog.Builder(this@MainActivity).setTitle("网络设置").setCancelable(false)
                        .setSingleChoiceItems(items, -1) { dialogInterface, i ->
                            when (i) {
                                0 -> {
                                    var intent: Intent? = null
                                    intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                                    startActivity(intent)
                                }
                                1 -> {
                                    var intent2: Intent? = null
                                    intent2 = Intent(Settings.ACTION_SETTINGS)
                                    startActivity(intent2)
                                }
                            }
                            dialogInterface.dismiss()
                        }.create().show()
                }
            }
            builder.setNegativeButton("取消") { dialogInterface, i -> dialogInterface.dismiss() }
            builder.show()
        }
        initView()
        initData()
        initPlatforms()
    }

    private fun initView() {
        main_rb = findViewById<View>(R.id.main_rb) as RadioGroup
    }

    private fun initData() {
        manager = supportFragmentManager
        homeFragment = HomeFragment()
        kyFragment = KaiyanFragment()
        videoFragment = VideoOldFragment()
        careFragment = CareOldFragment()
        val transaction = manager!!.beginTransaction()
        transaction.add(R.id.main_fl, homeFragment!!, "f1")
        transaction.add(R.id.main_fl, kyFragment!!, "f2")
        transaction.add(R.id.main_fl, videoFragment!!, "f3")
        transaction.add(R.id.main_fl, careFragment!!, "f4")
        transaction.show(homeFragment!!)
        transaction.hide(kyFragment!!)
        transaction.hide(videoFragment!!)
        transaction.hide(careFragment!!)
        transaction.commit()

        //默认选中首页
        main_rb!!.check(R.id.buttom_home_rb_id)
        main_rb!!.setOnCheckedChangeListener { radioGroup, i ->
            Jzvd.releaseAllVideos()
            when (i) {
                R.id.buttom_home_rb_id -> {
                    val transaction = manager!!.beginTransaction()
                    transaction.show(homeFragment!!)
                    transaction.hide(kyFragment!!)
                    transaction.hide(videoFragment!!)
                    transaction.hide(careFragment!!)
                    transaction.commit()
                }
                R.id.buttom_kaiyan_rb_id -> {
                    val transaction1 = manager!!.beginTransaction()
                    transaction1.show(kyFragment!!)
                    transaction1.hide(videoFragment!!)
                    transaction1.hide(homeFragment!!)
                    transaction1.hide(careFragment!!)
                    transaction1.commit()
                }
                 R.id.buttom_video_rb_id -> {
                    val transaction1 = manager!!.beginTransaction()
                    transaction1.show(videoFragment!!)
                    transaction1.hide(kyFragment!!)
                    transaction1.hide(homeFragment!!)
                    transaction1.hide(careFragment!!)
                    transaction1.commit()
                }
                R.id.buttom_care_rb_id -> {
                    val transaction2 = manager!!.beginTransaction()
                    transaction2.show(careFragment!!)
                    transaction2.hide(kyFragment!!)
                    transaction2.hide(videoFragment!!)
                    transaction2.hide(homeFragment!!)
                    transaction2.commit()
                }
            }
        }
    }

    private fun initPlatforms() {
        platforms.clear()
        for (e in list) {
            if (e.toString() != SHARE_MEDIA.GENERIC.toString()) {
                platforms.add(e.toSnsPlatform())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //3. 在onDestroy调用
        ChangeModeController.onDestory()
        UMShareAPI.get(this@MainActivity).release()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this@MainActivity).onActivityResult(requestCode, resultCode, data)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        UMShareAPI.get(this@MainActivity).onSaveInstanceState(outState)
    }

//    R.id.nights_ll -> flag = if (flag) {
//        ChangeModeController.changeNight(this@MainActivity, R.style.NightTheme)
//        false
//    } else {
//        ChangeModeController.changeDay(this@MainActivity, R.style.DayTheme)
//        true
//    }

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
        private const val CALLPHONE_CODE = 1
        private const val SETTINGS_CODE = 2
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}