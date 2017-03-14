package tianyinews.tianyi.com.tianyinews;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        //初始化侧滑界面
        initLeftMenu();
    }


    private void initView() {
        ImageView top_head = (ImageView) findViewById(R.id.top_head);

        top_head.setOnClickListener(this);
    }


    private void initLeftMenu() {
        slidingMenu = new SlidingMenu(MainActivity.this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(200);
        slidingMenu.setOffsetFadeDegree(0.4f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.left_layout);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_head:
                slidingMenu.showMenu();

                break;
        }
    }
}
