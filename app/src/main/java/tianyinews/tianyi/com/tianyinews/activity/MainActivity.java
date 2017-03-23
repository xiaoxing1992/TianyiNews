package tianyinews.tianyi.com.tianyinews.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.PullPushLayout;
import tianyinews.tianyi.com.tianyinews.base.ThemeManager;
import tianyinews.tianyi.com.tianyinews.fragment.HomeFragment;
import tianyinews.tianyi.com.tianyinews.fragment.LeftFragment;
import tianyinews.tianyi.com.tianyinews.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {


    protected SlidingMenu slidingMenu;
    private RadioGroup main_rb;
    private FragmentManager manager;
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private ActionBar supportActionBar;
    private ImageView qq_login_img;
    private RelativeLayout qq_user_login_jicheng;
    private RelativeLayout qq_user_rl;
    private ImageView qq_user_img;
    private TextView qq_user_name;
    private boolean flag = false;
    private LinearLayout nights_ll;
    //  private boolean isauth;
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ};
    private RelativeLayout qq_user_login_jicheng_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChangeModeController.getInstance().init(this, R.attr.class).setTheme(this, R.style.DayTheme, R.style.NightTheme);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //注册
        // ThemeManager.registerThemeChangeListener(this);
        //  supportActionBar = getSupportActionBar();
        initView();
        initData();
        //初始化侧滑界面
        initLeftMenu();
        initPlatforms();
    }


    private void initView() {
        main_rb = (RadioGroup) findViewById(R.id.main_rb);
        RadioButton buttom_home_rb_id = (RadioButton) findViewById(R.id.buttom_home_rb_id);

        RadioButton buttom_video_rb_id = (RadioButton) findViewById(R.id.buttom_video_rb_id);
        RadioButton buttom_care_rb_id = (RadioButton) findViewById(R.id.buttom_care_rb_id);
        //   isauth = UMShareAPI.get(MainActivity.this).isAuthorize(MainActivity.this, platforms.get(0).mPlatform);
    }

    private void initData() {
        manager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        videoFragment = new VideoFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fl, homeFragment);
        transaction.add(R.id.main_fl, videoFragment);

        transaction.show(homeFragment);
        transaction.hide(videoFragment);
        transaction.commit();

        //默认选中首页
        main_rb.check(R.id.buttom_home_rb_id);


        main_rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.buttom_home_rb_id:
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.show(homeFragment);
                        transaction.hide(videoFragment);
                        transaction.commit();
                        break;
                    case R.id.buttom_video_rb_id:
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.show(videoFragment);
                        transaction1.hide(homeFragment);
                        transaction1.commit();
                        break;
                    case R.id.buttom_care_rb_id:

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
        nights_ll = (LinearLayout) slidingMenu.findViewById(R.id.nights_ll);

        //设置QQ登录授权操作
        setQqLogin();


        nights_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {

                    ChangeModeController.changeNight(MainActivity.this, R.style.NightTheme);
                    flag = false;
                } else {
                    ChangeModeController.changeDay(MainActivity.this, R.style.DayTheme);
                    flag = true;

                }

               /* attrTypedValue = ChangeModeController.getAttrTypedValue(this, R.attr.zztextColor);
                toolbar.setTitleTextColor(getResources().getColor(attrTypedValue.resourceId));*/

                /*attrTypedValue = ChangeModeController.getAttrTypedValue(this, R.attr.zztextColor);
                toolbar.setTitleTextColor(getResources().getColor(attrTypedValue.resourceId));*/
            }
        });
    }

    private void setQqLogin() {
        qq_login_img = (ImageView) slidingMenu.findViewById(R.id.qq_login_img);
        qq_user_login_jicheng = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_login_jicheng);
        qq_user_rl = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_rl);
        qq_user_img = (ImageView) slidingMenu.findViewById(R.id.qq_user_img);
        qq_user_name = (TextView) slidingMenu.findViewById(R.id.qq_user_name);
        qq_user_login_jicheng_tv = (RelativeLayout) slidingMenu.findViewById(R.id.qq_user_login_jicheng_tv);
        qq_login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, platforms.get(0).mPlatform, authListener);
            }
        });
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
                    ImageOptions options = new ImageOptions.Builder()
                            .setCircular(true)
                            .setSize(400, 400)
                            .setLoadingDrawableId(R.mipmap.ic_launcher)
                            .build();
                    x.image().bind(qq_user_img, iconurl, options);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(MainActivity.this).onSaveInstanceState(outState);
    }

    UserOnListener userOnListener;

    public interface UserOnListener {
        void setUrl(String url);
    }

    public void getUrl(UserOnListener userOnListener) {
        this.userOnListener = userOnListener;
    }
}
