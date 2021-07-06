package tianyinews.tianyi.com.tianyinews.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

import cn.jzvd.Jzvd;
import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.PhoneUserBean;
import tianyinews.tianyi.com.tianyinews.bean.UserBean;
import tianyinews.tianyi.com.tianyinews.db.MyDataBaseHelper;
import tianyinews.tianyi.com.tianyinews.db.UserDao;
import tianyinews.tianyi.com.tianyinews.fragment.CareOldFragment;
import tianyinews.tianyi.com.tianyinews.fragment.HomeOldFragment;
import tianyinews.tianyi.com.tianyinews.fragment.VideoOldFragment;
import tianyinews.tianyi.com.tianyinews.util.ConnUtil;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int CALLPHONE_CODE = 1;
    private static final int SETTINGS_CODE = 2;
    protected SlidingMenu slidingMenu;
    private RadioGroup main_rb;
    private FragmentManager manager;
    private HomeOldFragment homeFragment;
    private VideoOldFragment videoFragment;
    private ImageView qq_login_img;
    private RelativeLayout qq_user_login_jicheng;
    private RelativeLayout qq_user_rl;
    private ImageView qq_user_img;
    private TextView qq_user_name;
    private boolean flag = false;
    //  private boolean isauth;
    UserOnListener userOnListener;
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ};
    private RelativeLayout qq_user_login_jicheng_tv;
    private CareOldFragment careFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChangeModeController.getInstance().init(this, R.attr.class).setTheme(this, R.style.DayTheme, R.style.NightTheme);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //进入主界面判断网络状态
        boolean netWorkAvailable = ConnUtil.isNetWorkAvailable(this);
        if (!netWorkAvailable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("网络未连接，是否开启网络连接？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final String items[] = new String[]{"WiFi", "移动数据"};
                    boolean isWifi = ConnUtil.isWiFi(MainActivity.this);
                    boolean isMobile = ConnUtil.isMobile(MainActivity.this);
                    if (!isWifi || !isMobile) {
                        new AlertDialog.Builder(MainActivity.this).setTitle("网络设置").setCancelable(false)
                                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                Intent intent = null;
                                                intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                                startActivity(intent);

                                                break;
                                            case 1:
                                                Intent intent2 = null;
                                                intent2 = new Intent(Settings.ACTION_SETTINGS);
                                                startActivity(intent2);
                                                break;
                                        }
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            builder.show();
        }
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        initView();
        initData();
        //初始化侧滑界面
        initLeftMenu();

        boolean config = SharedPreferencesUtil.getSharedConfig(MainActivity.this);
        if (config) {
            int sharedFlag = SharedPreferencesUtil.getSharedFlag(MainActivity.this);
            switch (sharedFlag) {
                case 1:
                    UserDao dao = new UserDao(MainActivity.this);
                    ArrayList<UserBean> userBeen = dao.queryUserByQQ();
                    for (UserBean ub : userBeen) {
                        qq_user_login_jicheng_tv.setVisibility(View.INVISIBLE);
                        qq_user_login_jicheng.setVisibility(View.GONE);
                        qq_user_rl.setVisibility(View.VISIBLE);
                        qq_user_name.setText(ub.name);

                        ImageOptions options = new ImageOptions.Builder()
                                .setCircular(true)
                                .setSize(400, 400)
                                .setLoadingDrawableId(R.mipmap.ic_launcher)
                                .build();
                        x.image().bind(qq_user_img, ub.imgUrl, options);
                        //       userOnListener.setUrl(ub.imgUrl);
                    }
                    break;
                case 2:
                    UserDao dao2 = new UserDao(MainActivity.this);
                    ArrayList<PhoneUserBean> phoneUserBeen = dao2.queryUserByPhone();
                    for (PhoneUserBean pb : phoneUserBeen) {
                        qq_user_login_jicheng_tv.setVisibility(View.INVISIBLE);
                        qq_user_login_jicheng.setVisibility(View.GONE);
                        qq_user_rl.setVisibility(View.VISIBLE);
                        qq_user_name.setText(pb.phonenumber);
                        qq_user_img.setImageResource(R.mipmap.ic_launcher);
                        //       userOnListener.setIdImg(R.mipmap.ic_launcher);
                    }

                    break;
            }


        }


        initPlatforms();
    }


    private void initView() {
        main_rb = (RadioGroup) findViewById(R.id.main_rb);
        //   isauth = UMShareAPI.get(MainActivity.this).isAuthorize(MainActivity.this, platforms.get(0).mPlatform);
    }

    private void initData() {
        manager = getSupportFragmentManager();

        homeFragment = new HomeOldFragment();
        videoFragment = new VideoOldFragment();
        careFragment = new CareOldFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fl, homeFragment, "f1");
        transaction.add(R.id.main_fl, videoFragment, "f2");
        transaction.add(R.id.main_fl, careFragment, "f3");
        transaction.show(homeFragment);
        transaction.hide(videoFragment);
        transaction.hide(careFragment);
        transaction.commit();

        //默认选中首页
        main_rb.check(R.id.buttom_home_rb_id);


        main_rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Jzvd.releaseAllVideos();
                switch (i) {
                    case R.id.buttom_home_rb_id:
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.show(homeFragment);

                        transaction.hide(videoFragment);
                        transaction.hide(careFragment);
                        transaction.commit();
                        break;
                    case R.id.buttom_video_rb_id:
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.show(videoFragment);
                        transaction1.hide(homeFragment);
                        transaction1.hide(careFragment);
                        transaction1.commit();
                        break;
                    case R.id.buttom_care_rb_id:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.show(careFragment);
                        transaction2.hide(videoFragment);
                        transaction2.hide(homeFragment);
                        transaction2.commit();
                        break;
                }
            }
        });
    }

    private void initLeftMenu() {
        slidingMenu = new SlidingMenu(MainActivity.this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(100);
        slidingMenu.setOffsetFadeDegree(0.4f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //  slidingMenu.setMenu(R.layout.left_frame_layout);
        slidingMenu.setMenu(R.layout.left_layout);
        // 得到transaction对象
       /* FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        //添加一个Fragment
        transaction.add(R.id.left_frame_layout_fl, new LeftFragment(), "f1");
        transaction.commit();*/
        LinearLayout nights_ll = (LinearLayout) slidingMenu.findViewById(R.id.nights_ll);
        LinearLayout settrings_ll = (LinearLayout) slidingMenu.findViewById(R.id.settrings_ll);
        ImageView cellphone_login_img = (ImageView) slidingMenu.findViewById(R.id.cellphone_login_img);
        //设置QQ登录授权操作
        setQqLogin();


        nights_ll.setOnClickListener(this);

        settrings_ll.setOnClickListener(this);
        cellphone_login_img.setOnClickListener(this);
    }

    //设置QQ登录授权操作
    private void setQqLogin() {
        qq_login_img = (ImageView) slidingMenu.findViewById(R.id.qq_login_img);
        qq_login_img.setOnClickListener(this);
        qq_user_login_jicheng = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_login_jicheng);
        qq_user_rl = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_rl);
        qq_user_img = (ImageView) slidingMenu.findViewById(R.id.qq_user_img);
        qq_user_name = (TextView) slidingMenu.findViewById(R.id.qq_user_name);
        qq_user_login_jicheng_tv = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_login_jicheng_tv);

    }

    UMAuthListener authListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> data) {

            switch (action) {
                case ACTION_AUTHORIZE:
                    UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, platforms.get(0).mPlatform, authListener);
                    break;
                case ACTION_DELETE:
                    break;
                case ACTION_GET_PROFILE:

                    qq_user_login_jicheng_tv.setVisibility(View.INVISIBLE);
                    qq_user_login_jicheng.setVisibility(View.GONE);
                    qq_user_rl.setVisibility(View.VISIBLE);
                    String name = data.get("screen_name");
                    qq_user_name.setText(name);
                   /* String gender = data.get("gender");
                    tvvv_tv.setText(gender);*/
                    String iconurl = data.get("iconurl");
                    userOnListener.setUrl(iconurl);
                    skinOnListener.setSkin(1002);
                    ImageOptions options = new ImageOptions.Builder()
                            .setCircular(true)
                            .setSize(400, 400)
                            .setLoadingDrawableId(R.mipmap.ic_launcher)
                            .build();
                    x.image().bind(qq_user_img, iconurl, options);

                    //登录成功后将数据保存到本地数据库
                    SharedPreferencesUtil.putSharedConfig(MainActivity.this, true);
                    SharedPreferencesUtil.putSharedFlag(MainActivity.this, 1);
                    UserDao dao = new UserDao(MainActivity.this);
                    dao.insertUserByQQ(data.get("uid"), name, iconurl);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(MainActivity.this, "请求取消", Toast.LENGTH_SHORT).show();
        }
    };

    private void initPlatforms() {
        platforms.clear();
        for (SHARE_MEDIA e : list) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())) {
                platforms.add(e.toSnsPlatform());
            }
        }
    }

    //显示侧滑的方法
    public void showLeftMenu() {
        slidingMenu.showMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //3. 在onDestroy调用

        ChangeModeController.onDestory();
        UMShareAPI.get(MainActivity.this).release();


        //  JetPlayer.getJetPlayer().pause();
        // JetPlayer.getJetPlayer().release();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALLPHONE_CODE && resultCode == RESULT_OK) {
            SharedPreferencesUtil.putSharedFlag(MainActivity.this, 2);
            SharedPreferencesUtil.putSharedConfig(MainActivity.this, true);
            qq_user_login_jicheng_tv.setVisibility(View.INVISIBLE);
            qq_user_login_jicheng.setVisibility(View.GONE);
            qq_user_rl.setVisibility(View.VISIBLE);
            int telephone = data.getIntExtra("telephone", 0);
            qq_user_name.setText(telephone + "");
            qq_user_img.setImageResource(R.mipmap.ic_launcher);
            userOnListener.setImg(101);
            skinOnListener.setSkin(1000);
            /*  ImageOptions options = new ImageOptions.Builder()
                    .setCircular(true)
                    .setSize(400, 400)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .build();
            x.image().bind(qq_user_img, R.mipmap.ic_launcher, options);*/
            //将数据存入数据库
            UserDao dao = new UserDao(MainActivity.this);
            dao.insertUserByPhone(telephone, "Tianyi", "");
        } else if (requestCode == SETTINGS_CODE && resultCode == SettingActivity.RESULT_SETTRINGS_CODE) {
            SharedPreferencesUtil.putSharedConfig(MainActivity.this, false);
            UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, platforms.get(0).mPlatform, authListener);
            qq_user_rl.setVisibility(View.GONE);
            qq_user_login_jicheng_tv.setVisibility(View.VISIBLE);
            qq_user_login_jicheng.setVisibility(View.VISIBLE);
            userOnListener.setUrl("");
            skinOnListener.setSkin(1001);

        } else if (requestCode == SETTINGS_CODE && resultCode == SettingActivity.RESULT_SETTRINGS_CODE_PHONE) {
            SharedPreferencesUtil.putSharedConfig(MainActivity.this, false);
            qq_user_rl.setVisibility(View.GONE);
            qq_user_login_jicheng_tv.setVisibility(View.VISIBLE);
            qq_user_login_jicheng.setVisibility(View.VISIBLE);
            userOnListener.setUrl("");
            skinOnListener.setSkin(1003);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(MainActivity.this).onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qq_login_img:
                UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, platforms.get(0).mPlatform, authListener);
                break;
            case R.id.nights_ll:
                if (flag) {

                    ChangeModeController.changeNight(MainActivity.this, R.style.NightTheme);
                    flag = false;
                } else {
                    ChangeModeController.changeDay(MainActivity.this, R.style.DayTheme);
                    flag = true;

                }

                break;
            case R.id.cellphone_login_img:
                Intent intent = new Intent(MainActivity.this, CallphoneActivity.class);
                startActivityForResult(intent, CALLPHONE_CODE);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);


                break;

            case R.id.settrings_ll:
                Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent2, SETTINGS_CODE);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

                break;
        }
    }


    public interface UserOnListener {
        void setUrl(String url);

        void setImg(int i);

    }

    public void getUrl(UserOnListener userOnListener) {
        this.userOnListener = userOnListener;
    }

    SkinOnListener skinOnListener;

    public interface SkinOnListener {
        void setSkin(int i);
    }

    public void getSkin(SkinOnListener skinOnListener) {
        this.skinOnListener = skinOnListener;
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
