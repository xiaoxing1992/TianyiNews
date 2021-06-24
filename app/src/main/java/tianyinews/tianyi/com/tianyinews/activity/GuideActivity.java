package tianyinews.tianyi.com.tianyinews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.util.CacheUtil;
import tianyinews.tianyi.com.tianyinews.util.PxDpUtil;

public class GuideActivity extends AppCompatActivity {

    private ViewPager guide_viewpager;
    private ArrayList<ImageView> imgList;
    private LinearLayout guide_ll;
    private View view;
    private ImageView guide_red_point;
    private int maginLeft;
    private Button guide_jingru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ImmersionBar.with(this).transparentStatusBar().init();
        boolean b = CacheUtil.getBooleans(GuideActivity.this, false);
        if(b){
            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
        initView();
        initData();

        initScroll();

        guide_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            guide_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            //两点间的距离
            maginLeft = guide_ll.getChildAt(1).getLeft() - guide_ll.getChildAt(0).getLeft();
        }


    }

    private void initView() {
        guide_viewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        guide_ll = (LinearLayout) findViewById(R.id.guide_ll);
        guide_red_point = (ImageView) findViewById(R.id.guide_red_point);

        guide_jingru = (Button) findViewById(R.id.guide_jingru);
    }

    private void initData() {
        int dip = PxDpUtil.dip2px(this, 10);
        Log.e("dip==", dip + "");
        int dip2 = PxDpUtil.dip2px(this, 18);

        int[] a = new int[]{R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_three, R.drawable.guide_four};
        imgList = new ArrayList<ImageView>();

        for (int i = 0; i < a.length; i++) {
            ImageView imageView = new ImageView(this);
            ImageView imageView2 = new ImageView(this);
            imageView.setImageResource(a[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView2.setBackgroundResource(R.drawable.guide_points_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip, dip);
            if (i != 0) {
                params.leftMargin = dip2;
            }
            imgList.add(imageView);
            imageView2.setLayoutParams(params);
            guide_ll.addView(imageView2);
        }

    }


    private void initScroll() {
        guide_viewpager.setAdapter(new MyPagerAdapter());

        guide_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float leftMaginTwo = (position + positionOffset) * maginLeft;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) guide_red_point.getLayoutParams();
                layoutParams.leftMargin = (int) leftMaginTwo * 2;
                guide_red_point.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgList.size() - 1) {
                    guide_jingru.setVisibility(View.VISIBLE);
                } else {
                    guide_jingru.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        guide_jingru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheUtil.putBooleans(GuideActivity.this,true);
                Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgList.get(position));
            return imgList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
