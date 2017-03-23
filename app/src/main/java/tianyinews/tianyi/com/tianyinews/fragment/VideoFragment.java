package tianyinews.tianyi.com.tianyinews.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.trs.channellib.channel.channel.helper.ChannelDataHelepr;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.MyChannel;
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView;
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.HomeChildFragment;
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.VideoChildFragment;
import tianyinews.tianyi.com.tianyinews.util.GsonUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class VideoFragment extends BaseFragment {
    // implements ChannelDataHelepr.ChannelDataRefreshListenter
  /*  private String tabtitles[] = new String[]{"娱乐", "体育", "搞笑", "黑科技", "美女", "新闻现场", "BoBo", "军武", "涨知识", "八卦"};
    private List<String> mDataList = Arrays.asList(tabtitles);*/
    private TabLayout video_tab_layout;
    private ViewPager video_view_pager;
    //  private ImageView button_more_video_columns;
    private ArrayList<BaseFragment> fragList;
    // int id = 1;
    // Map<String, Integer> IdsMap = new HashMap<>();
    // List<String> preIds = new ArrayList<>();
    // List<MyChannel> myChannels;
    //  private int needShowPosition = -1;
    //   private MyHomeListViewPager adapter;
    // ChannelDataHelepr<MyChannel> dataHelepr;
    MyHomeListViewPager adapter;
    private List<MyChannel> alldata;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.videofragment, null);
        video_tab_layout = (TabLayout) view.findViewById(R.id.video_tab_layout);
        video_view_pager = (ViewPager) view.findViewById(R.id.video_view_pager);
        alldata = new ArrayList<>();
        //   button_more_video_columns = (ImageView) view.findViewById(R.id.button_more_video_columns);
        //     dataHelepr = new ChannelDataHelepr(mContext, VideoFragment.this, view.findViewById(R.id.rl_ll_video));
        //     dataHelepr.setSwitchView(button_more_video_columns);
        //    loadData();
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        loadData();
        fragList = new ArrayList<>();
        adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        video_view_pager.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        video_tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < alldata.size(); i++) {
            video_tab_layout.addTab(video_tab_layout.newTab().setText(alldata.get(i).title));
        }

        MyHomeListViewPager adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        video_view_pager.setAdapter(adapter);
        video_tab_layout.setupWithViewPager(video_view_pager);
        video_tab_layout.setTabsFromPagerAdapter(adapter);
    }

   /* @Override
    public void updateData() {
        loadData();
    }*/
/*
    @Override
    public void onChannelSeleted(boolean update, int posisiton) {

    }*/

    private void loadData() {


        String data = getFromRaw();
        alldata = GsonUtil.jsonToBeanList(data, MyChannel.class);


    }

    /*private void setPointer() {

        video_tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < mDataList.size(); i++) {
            video_tab_layout.addTab(video_tab_layout.newTab().setText(mDataList.get(i)));
        }

        MyHomeListViewPager adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        video_view_pager.setAdapter(adapter);
        video_tab_layout.setupWithViewPager(video_view_pager);
        video_tab_layout.setTabsFromPagerAdapter(adapter);

            mDataList.clear();
    }*/

    private String getFromRaw() {
        String result = "";
        try {
            InputStream input = getResources().openRawResource(R.raw.video_list);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            output.close();
            input.close();

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    class MyHomeListViewPager extends FragmentPagerAdapter {

        public MyHomeListViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            VideoChildFragment videoChildFragment = new VideoChildFragment();
            Bundle bundle = new Bundle();
            //      bundle.putString(HomeChildFragment.KEY_TITLE, alldata.get(position));
            bundle.putString(VideoChildFragment.KEY_URL, alldata.get(position).url);
            bundle.putString(VideoChildFragment.KEY_URL_FOOTER, alldata.get(position).url_footer);
            videoChildFragment.setArguments(bundle);
            return videoChildFragment;
        }

        @Override
        public int getCount() {
            return alldata.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return alldata.get(position).title;
        }


















        /* @Override
        public long getItemId(int position) {
            return IdsMap.get(getPageTitle(position));
        }

        @Override
        public int getItemPosition(Object object) {
            VideoChildFragment fragment = (VideoChildFragment) object;
            String title = fragment.getTitle();
            int preId = preIds.indexOf(title);
            int newId = -1;
            int i = 0;
            int size = getCount();
            for (; i < size; i++) {
                if (getPageTitle(i).equals(title)) {
                    newId = i;
                    break;
                }
            }
            if (newId != -1 && newId == preId) {
                return POSITION_UNCHANGED;
            }
            if (newId != -1) {
                return newId;
            }
            return POSITION_NONE;
        }

        @Override
        public void notifyDataSetChanged() {
            for (MyChannel info : myChannels) {
                if (!IdsMap.containsKey(info.title)) {
                    IdsMap.put(info.title, id++);
                }
            }
            super.notifyDataSetChanged();
            preIds.clear();
            int size = getCount();
            for (int i = 0; i < size; i++) {
                preIds.add((String) getPageTitle(i));
            }
        }*/
    }
}
