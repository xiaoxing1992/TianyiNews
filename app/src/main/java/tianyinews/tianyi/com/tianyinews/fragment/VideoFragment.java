package tianyinews.tianyi.com.tianyinews.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class VideoFragment extends BaseFragment {
    private String tabtitles[] = new String[]{"推荐", "热点", "视频", "乐山", "社会", "头条号", "问答", "娱乐", "图片", "科技", "汽车", "体育", "财经", "军事", "国际",};
    //  private List<String> mDataList = Arrays.asList(tabtitles);
    private ArrayList<String> tabTitleList = new ArrayList<>();
    private TabLayout video_tab_layout;
    private ViewPager video_view_pager;
    private ArrayList<BaseFragment> fragList;
    //   private MagicIndicator main_magic_indicator;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.videofragment, null);
        video_tab_layout = (TabLayout) view.findViewById(R.id.video_tab_layout);
        //  main_magic_indicator = (MagicIndicator) view.findViewById(R.id.main_magic_indicator);
        video_view_pager = (ViewPager) view.findViewById(R.id.video_view_pager);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        fragList = new ArrayList<>();
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        fragList.add(new HomeChildFragment());
        for (int i = 0; i < tabtitles.length; i++) {
            tabTitleList.add(tabtitles[i]);
        }


      /*  main_magic_indicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.8f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                // SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#D43D3D"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        home_view_pager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#D43D3D"));
                return indicator;
            }
        });
        main_magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(main_magic_indicator, home_view_pager);*/
       /* MyHomeListViewPager adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        home_view_pager.setAdapter(adapter);*/
        video_tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < tabTitleList.size(); i++) {
            video_tab_layout.addTab(video_tab_layout.newTab().setText(tabTitleList.get(i)));
        }

        MyHomeListViewPager adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        video_view_pager.setAdapter(adapter);
        video_tab_layout.setupWithViewPager(video_view_pager);
        video_tab_layout.setTabsFromPagerAdapter(adapter);
    }

    class MyHomeListViewPager extends FragmentPagerAdapter {

        public MyHomeListViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitleList.get(position);
        }
    }
}
