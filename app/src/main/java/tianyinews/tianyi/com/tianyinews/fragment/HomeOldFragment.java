package tianyinews.tianyi.com.tianyinews.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.trs.channellib.channel.channel.helper.ChannelDataHelepr;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.activity.MainActivity;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.BaseOldFragment;
import tianyinews.tianyi.com.tianyinews.bean.MyChannel;
import tianyinews.tianyi.com.tianyinews.bean.PhoneUserBean;
import tianyinews.tianyi.com.tianyinews.bean.UserBean;
import tianyinews.tianyi.com.tianyinews.db.UserDao;
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView;
import tianyinews.tianyi.com.tianyinews.fragment.childfragment.HomeChildFragment;
import tianyinews.tianyi.com.tianyinews.util.GsonUtil;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;


/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class HomeOldFragment extends BaseOldFragment implements ChannelDataHelepr.ChannelDataRefreshListenter {
    private List<String> mDataList = new ArrayList<>();
    int id = 1;
    Map<String, Integer> IdsMap = new HashMap<>();
    List<String> preIds = new ArrayList<>();
    private ViewPager home_view_pager;
    private MagicIndicator main_magic_indicator;
    public ImageView top_head;
    private ImageView button_more_columns;
    private MyHomeListViewPager adapter;
    ChannelDataHelepr<MyChannel> dataHelepr;
    List<MyChannel> myChannels;
    private int needShowPosition = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.homefragment;
    }

    @Override
    protected void initView(@NonNull View view) {
        ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init();

        main_magic_indicator =  view.findViewById(R.id.main_magic_indicator);
        myChannels = new ArrayList<>();
        home_view_pager =  view.findViewById(R.id.home_view_pager);
        top_head =  view.findViewById(R.id.top_head);

        button_more_columns = (ImageView) view.findViewById(R.id.button_more_columns);
        dataHelepr = new ChannelDataHelepr(mContext, HomeOldFragment.this, view.findViewById(R.id.rl_ll));
        dataHelepr.setSwitchView(button_more_columns);
        boolean config = SharedPreferencesUtil.getSharedConfig(mContext);
        if (config) {
            int sharedFlag = SharedPreferencesUtil.getSharedFlag(mContext);
            switch (sharedFlag) {
                case 1:

                    UserDao dao = new UserDao(mContext);
                    ArrayList<UserBean> userBeen = dao.queryUserByQQ();
                    for (UserBean ub : userBeen) {

                        ImageOptions options = new ImageOptions.Builder()
                                .setCircular(true)
                                .setSize(400, 400)
                                .setLoadingDrawableId(R.mipmap.ic_launcher)
                                .build();
                        x.image().bind(top_head, ub.imgUrl, options);
                    }
                    break;
                case 2:
                    UserDao dao2 = new UserDao(mContext);
                    ArrayList<PhoneUserBean> phoneUserBeen = dao2.queryUserByPhone();
                    for (PhoneUserBean pb : phoneUserBeen) {
                        top_head.setImageResource(R.mipmap.ic_launcher);
                        //       userOnListener.setIdImg(R.mipmap.ic_launcher);
                    }

                    break;
            }


        }


        loadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).statusBarColorTransformEnable(false).statusBarColor(R.color.dayTitleBackground).init();
    }

    @Override
    protected void initData() {
        super.initData();


        adapter = new MyHomeListViewPager(getActivity().getSupportFragmentManager());
        home_view_pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        top_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showLeftMenu();
            }
        });
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getUrl(new MainActivity.UserOnListener() {
            @Override
            public void setUrl(String url) {
                if (url != null) {
                    if (url.equals("")) {
                        top_head.setImageResource(R.drawable.default_round_head);
                    } else {
                        ImageOptions options = new ImageOptions.Builder()
                                .setCircular(true)
                                .setSize(400, 400)
                                .setLoadingDrawableId(R.mipmap.ic_launcher)
                                .build();
                        x.image().bind(top_head, url, options);
                    }
                }
            }

            @Override
            public void setImg(int i) {
                if (i == 101) {
                    top_head.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
    }

    private void setPointer() {
        //代码设置指示器背景颜色,会出现夜间模式不应用
        ChangeModeController.getInstance().addBackgroundColor(main_magic_indicator, R.attr.zzbackground);


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.8f);
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
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
        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        commonNavigatorAdapter.notifyDataSetChanged();
        main_magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(main_magic_indicator, home_view_pager);
        adapter.notifyDataSetChanged();
        mDataList.clear();
    }

    @Override
    public void updateData() {
        loadData();
    }

    @Override
    public void onChannelSeleted(boolean update, int posisiton) {

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String data = getFromRaw();
                List<MyChannel> alldata = GsonUtil.jsonToBeanList(data, MyChannel.class);
                final List<MyChannel> showChannels = dataHelepr.getShowChannels(alldata);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myChannels.clear();
                        myChannels.addAll(showChannels);
                        adapter.notifyDataSetChanged();
                        if (needShowPosition != -1) {
                            home_view_pager.setCurrentItem(needShowPosition);
                            needShowPosition = -1;
                        }
                        for (int i = 0; i < myChannels.size(); i++) {
                            mDataList.add(myChannels.get(i).title);
                        }
                        setPointer();

                    }
                });

            }
        }).start();
    }

    class MyHomeListViewPager extends FragmentPagerAdapter {

        public MyHomeListViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            HomeChildFragment homeChildFragment = new HomeChildFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(HomeChildFragment.MODEL_KEY, myChannels.get(position));
            homeChildFragment.setArguments(bundle);
            return homeChildFragment;
        }

        @Override
        public int getCount() {
            return myChannels.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myChannels.get(position).title;
        }

        @Override
        public long getItemId(int position) {
            return IdsMap.get(getPageTitle(position));
        }

        @Override
        public int getItemPosition(Object object) {
            HomeChildFragment fragment = (HomeChildFragment) object;
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
        }
    }

    private String getFromRaw() {
        String result = "";
        try {
            InputStream input = getResources().openRawResource(R.raw.news_list);
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


    public interface HomeFragmentOnListener {
        void setView(ImageView view);

    }

    HomeFragmentOnListener homeFragmentOnListener;

    public void getMyView(HomeFragmentOnListener homeFragmentOnListener) {
        this.homeFragmentOnListener = homeFragmentOnListener;
    }
}
