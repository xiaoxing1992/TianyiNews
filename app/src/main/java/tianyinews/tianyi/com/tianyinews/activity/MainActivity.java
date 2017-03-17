package tianyinews.tianyi.com.tianyinews.activity;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

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
    }


    private void initView() {
        main_rb = (RadioGroup) findViewById(R.id.main_rb);
        RadioButton buttom_home_rb_id = (RadioButton) findViewById(R.id.buttom_home_rb_id);

        RadioButton buttom_video_rb_id = (RadioButton) findViewById(R.id.buttom_video_rb_id);
        RadioButton buttom_care_rb_id = (RadioButton) findViewById(R.id.buttom_care_rb_id);

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
        slidingMenu.setMenu(R.layout.left_frame_layout);
        // 得到transaction对象
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        //添加一个Fragment
        transaction.add(R.id.left_frame_layout_fl, new LeftFragment());
        transaction.commit();


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
    }
}
