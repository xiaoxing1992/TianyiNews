package tianyinews.tianyi.com.tianyinews.fragment.childfragment;

import android.media.JetPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.weiwei.xlistviewlibrary.XListView;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter;
import tianyinews.tianyi.com.tianyinews.adapter.MyVideoListViewAdapter;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.bean.VideoBean;
import tianyinews.tianyi.com.tianyinews.util.JsonUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class VideoChildFragment extends BaseFragment {
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_URL = "key_url";
    public static final String KEY_URL_FOOTER = "key_url_footer";
    private XListView home_xlist_view;
    private Handler handler = new Handler();
    private int PageIndex = 0;
    private List<VideoBean> newData = new ArrayList<>();
    private MyVideoListViewAdapter adapter;
    private String title;
    private String url;
    private String url_footer;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.homechildfragment, null);
        home_xlist_view = (XListView) view.findViewById(R.id.home_xlist_view);


        Bundle bundle = getArguments();
        //   title = bundle.getString(KEY_TITLE, "");
        url = bundle.getString(KEY_URL, "");
        url_footer = bundle.getString(KEY_URL_FOOTER, "");
        adapter = new MyVideoListViewAdapter(getActivity(), newData);
        home_xlist_view.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();


        home_xlist_view.setPullRefreshEnable(true);
        home_xlist_view.setPullLoadEnable(true);
        home_xlist_view.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                newData.clear();
                PageIndex = 0;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getServerData();

                        home_xlist_view.stopRefresh();

                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                PageIndex += 10;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getServerData();
                        home_xlist_view.stopLoadMore();
                    }
                }, 1000);
            }
        });
        //获取网络数据
        getServerData();

    }

    private void getServerData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String http_url = url + PageIndex + url_footer;
        //  Log.e("sadddddddddd", http_url);
        asyncHttpClient.get(getContext(), http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<VideoBean> videoJson = JsonUtil.getVideoJson(responseString);

                newData.addAll(videoJson);
                adapter.notifyDataSetChanged();


            }
        });
    }

   /* public String getTitle() {
        return title;
    }*/

}
