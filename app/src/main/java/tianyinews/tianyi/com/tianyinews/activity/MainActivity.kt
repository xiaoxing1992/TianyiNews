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
import coil.load
import coil.transform.CircleCropTransformation
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.shareboard.SnsPlatform
import thinkfreely.changemodelibrary.ChangeModeController
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.activity.MainActivity
import tianyinews.tianyi.com.tianyinews.db.MyDataBaseHelper
import tianyinews.tianyi.com.tianyinews.db.UserDao
import tianyinews.tianyi.com.tianyinews.fragment.CareOldFragment
import tianyinews.tianyi.com.tianyinews.fragment.HomeFragment
import tianyinews.tianyi.com.tianyinews.fragment.KaiyanFragment
import tianyinews.tianyi.com.tianyinews.fragment.VideoOldFragment
import tianyinews.tianyi.com.tianyinews.util.ConnUtil
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val slidingMenu: SlidingMenu by lazy {
        SlidingMenu(this).apply {
            mode = SlidingMenu.LEFT
            behindOffset = 100
            setOffsetFadeDegree(0.4f)
            attachToActivity(this@MainActivity, SlidingMenu.SLIDING_CONTENT)
            setMenu(R.layout.left_layout)
        }
    }
    private var main_rb: RadioGroup? = null
    private var manager: FragmentManager? = null
    private var homeFragment: HomeFragment? = null
    private var kyFragment: KaiyanFragment? = null
    private var videoFragment: VideoOldFragment? = null
    private var qq_login_img: ImageView? = null
    private var qq_user_login_jicheng: RelativeLayout? = null
    private var qq_user_rl: RelativeLayout? = null
    private var qq_user_img: ImageView? = null
    private var qq_user_name: TextView? = null
    private var flag = false

    //  private boolean isauth;
    var userOnListener: UserOnListener? = null
    var platforms = ArrayList<SnsPlatform>()
    private val list = arrayOf(SHARE_MEDIA.QQ)
    private var qq_user_login_jicheng_tv: RelativeLayout? = null
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
        val dataBaseHelper = MyDataBaseHelper(this)
        initView()
        initData()
        //初始化侧滑界面
        initLeftMenu()
        val config = SharedPreferencesUtil.getSharedConfig(this@MainActivity)
        if (config) {
            val sharedFlag = SharedPreferencesUtil.getSharedFlag(this@MainActivity)
            when (sharedFlag) {
                1 -> {
                    val dao = UserDao(this@MainActivity)
                    val userBeen = dao.queryUserByQQ()
                    for (ub in userBeen) {
                        qq_user_login_jicheng_tv!!.visibility = View.INVISIBLE
                        qq_user_login_jicheng!!.visibility = View.GONE
                        qq_user_rl!!.visibility = View.VISIBLE
                        qq_user_name!!.text = ub.name
                        qq_user_img?.load(ub.imgUrl){
                            size(400, 400)
                            placeholder(R.mipmap.ic_launcher)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
                2 -> {
                    val dao2 = UserDao(this@MainActivity)
                    val phoneUserBeen = dao2.queryUserByPhone()
                    for (pb in phoneUserBeen) {
                        qq_user_login_jicheng_tv!!.visibility = View.INVISIBLE
                        qq_user_login_jicheng!!.visibility = View.GONE
                        qq_user_rl!!.visibility = View.VISIBLE
                        qq_user_name!!.text = pb.phonenumber
                        qq_user_img!!.setImageResource(R.mipmap.ic_launcher)
                        //       userOnListener.setIdImg(R.mipmap.ic_launcher);
                    }
                }
            }
        }
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

    private fun initLeftMenu() {
        val nights_ll = slidingMenu!!.findViewById<View>(R.id.nights_ll) as LinearLayout
        val settrings_ll = slidingMenu!!.findViewById<View>(R.id.settrings_ll) as LinearLayout
        val cellphone_login_img = slidingMenu!!.findViewById<View>(R.id.cellphone_login_img) as ImageView
        //设置QQ登录授权操作
        setQqLogin()
        nights_ll.setOnClickListener(this)
        settrings_ll.setOnClickListener(this)
        cellphone_login_img.setOnClickListener(this)
    }

    //设置QQ登录授权操作
    private fun setQqLogin() {
        qq_login_img = slidingMenu!!.findViewById<View>(R.id.qq_login_img) as ImageView
        qq_login_img!!.setOnClickListener(this)
        qq_user_login_jicheng = slidingMenu!!.findViewById<View>(R.id.qq_user_login_jicheng) as RelativeLayout
        qq_user_rl = slidingMenu!!.findViewById<View>(R.id.qq_user_rl) as RelativeLayout
        qq_user_img = slidingMenu!!.findViewById<View>(R.id.qq_user_img) as ImageView
        qq_user_name = slidingMenu!!.findViewById<View>(R.id.qq_user_name) as TextView
        qq_user_login_jicheng_tv = slidingMenu!!.findViewById<View>(R.id.qq_user_login_jicheng_tv) as RelativeLayout
    }

    var authListener: UMAuthListener = object : UMAuthListener {
        override fun onStart(share_media: SHARE_MEDIA) {}
        override fun onComplete(share_media: SHARE_MEDIA, action: Int, data: Map<String, String>) {
            when (action) {
                UMAuthListener.ACTION_AUTHORIZE -> UMShareAPI.get(this@MainActivity).getPlatformInfo(this@MainActivity, platforms[0].mPlatform, this)
                UMAuthListener.ACTION_DELETE -> {
                }
                UMAuthListener.ACTION_GET_PROFILE -> {
                    qq_user_login_jicheng_tv!!.visibility = View.INVISIBLE
                    qq_user_login_jicheng!!.visibility = View.GONE
                    qq_user_rl!!.visibility = View.VISIBLE
                    val name = data["screen_name"]
                    qq_user_name!!.text = name
                    /* String gender = data.get("gender");
                    tvvv_tv.setText(gender);*/
                    val iconurl = data["iconurl"]
                    userOnListener!!.setUrl(iconurl)
                    skinOnListener!!.setSkin(1002)
                    qq_user_img?.load(iconurl){
                        size(400, 400)
                        placeholder(R.mipmap.ic_launcher)
                        transformations(CircleCropTransformation())
                    }
                    //登录成功后将数据保存到本地数据库
                    SharedPreferencesUtil.putSharedConfig(this@MainActivity, true)
                    SharedPreferencesUtil.putSharedFlag(this@MainActivity, 1)
                    val dao = UserDao(this@MainActivity)
                    dao.insertUserByQQ(data["uid"], name, iconurl)
                }
                else -> {
                }
            }
        }

        override fun onError(share_media: SHARE_MEDIA, i: Int, throwable: Throwable) {
            Toast.makeText(this@MainActivity, "请求失败", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(share_media: SHARE_MEDIA, i: Int) {
            Toast.makeText(this@MainActivity, "请求取消", Toast.LENGTH_SHORT).show()
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

    //显示侧滑的方法
    fun showLeftMenu() {
        slidingMenu!!.showMenu()
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
        if (requestCode == CALLPHONE_CODE && resultCode == RESULT_OK) {
            SharedPreferencesUtil.putSharedFlag(this@MainActivity, 2)
            SharedPreferencesUtil.putSharedConfig(this@MainActivity, true)
            qq_user_login_jicheng_tv!!.visibility = View.INVISIBLE
            qq_user_login_jicheng!!.visibility = View.GONE
            qq_user_rl!!.visibility = View.VISIBLE
            val telephone = data!!.getIntExtra("telephone", 0)
            qq_user_name!!.text = telephone.toString() + ""
            qq_user_img!!.setImageResource(R.mipmap.ic_launcher)
            userOnListener!!.setImg(101)
            skinOnListener!!.setSkin(1000)
            /*  ImageOptions options = new ImageOptions.Builder()
                    .setCircular(true)
                    .setSize(400, 400)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .build();
            x.image().bind(qq_user_img, R.mipmap.ic_launcher, options);*/
            //将数据存入数据库
            val dao = UserDao(this@MainActivity)
            dao.insertUserByPhone(telephone, "Tianyi", "")
        } else if (requestCode == SETTINGS_CODE && resultCode == SettingActivity.RESULT_SETTRINGS_CODE) {
            SharedPreferencesUtil.putSharedConfig(this@MainActivity, false)
            UMShareAPI.get(this@MainActivity).deleteOauth(this@MainActivity, platforms[0].mPlatform, authListener)
            qq_user_rl!!.visibility = View.GONE
            qq_user_login_jicheng_tv!!.visibility = View.VISIBLE
            qq_user_login_jicheng!!.visibility = View.VISIBLE
            userOnListener!!.setUrl("")
            skinOnListener!!.setSkin(1001)
        } else if (requestCode == SETTINGS_CODE && resultCode == SettingActivity.RESULT_SETTRINGS_CODE_PHONE) {
            SharedPreferencesUtil.putSharedConfig(this@MainActivity, false)
            qq_user_rl!!.visibility = View.GONE
            qq_user_login_jicheng_tv!!.visibility = View.VISIBLE
            qq_user_login_jicheng!!.visibility = View.VISIBLE
            userOnListener!!.setUrl("")
            skinOnListener!!.setSkin(1003)
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        UMShareAPI.get(this@MainActivity).onSaveInstanceState(outState)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.qq_login_img -> UMShareAPI.get(this@MainActivity).doOauthVerify(this@MainActivity, platforms[0].mPlatform, authListener)
            R.id.nights_ll -> flag = if (flag) {
                ChangeModeController.changeNight(this@MainActivity, R.style.NightTheme)
                false
            } else {
                ChangeModeController.changeDay(this@MainActivity, R.style.DayTheme)
                true
            }
            R.id.cellphone_login_img -> {
                val intent = Intent(this@MainActivity, CallphoneActivity::class.java)
                startActivityForResult(intent, CALLPHONE_CODE)
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
            }
            R.id.settrings_ll -> {
                val intent2 = Intent(this@MainActivity, SettingActivity::class.java)
                startActivityForResult(intent2, SETTINGS_CODE)
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
            }
        }
    }

    interface UserOnListener {
        fun setUrl(url: String?)
        fun setImg(i: Int)
    }

    fun getUrl(userOnListener: UserOnListener?) {
        this.userOnListener = userOnListener
    }

    var skinOnListener: SkinOnListener? = null

    interface SkinOnListener {
        fun setSkin(i: Int)
    }

    fun getSkin(skinOnListener: SkinOnListener?) {
        this.skinOnListener = skinOnListener
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
        private const val CALLPHONE_CODE = 1
        private const val SETTINGS_CODE = 2
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}