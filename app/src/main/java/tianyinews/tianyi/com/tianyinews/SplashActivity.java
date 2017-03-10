package tianyinews.tianyi.com.tianyinews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2017/3/10.
 */

public class SplashActivity extends Activity {

    private AlphaAnimation alphaAnimation;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(SplashActivity.this, R.layout.splash_layout, null);
        setContentView(view);
        initData();
    }

    private void initData() {
        alphaAnimation = new AlphaAnimation(0.3f, 1f);
        alphaAnimation.setDuration(3000);
        view.setAnimation(alphaAnimation);
        view.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });
    }

    private void startTo() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
