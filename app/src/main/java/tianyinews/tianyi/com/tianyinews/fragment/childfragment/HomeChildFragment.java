package tianyinews.tianyi.com.tianyinews.fragment.childfragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.weiwei.xlistviewlibrary.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.util.JsonUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class HomeChildFragment extends BaseFragment {
    public static final java.lang.String KEY_TITLE = "key_title";
    public static final java.lang.String KEY_URL = "key_url";
    public static final java.lang.String KEY_URL_FOOTER = "url_footer";
    private XListView home_xlist_view;
    private Handler handler = new Handler();
    private int PageIndex = 1;
    private List<HoltBean.DataBean> newData = new ArrayList<>();
    private MyHomeListViewAdapter adapter;
    private String title;
    private String url;
    private String url_footer;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.homechildfragment, null);
        home_xlist_view = (XListView) view.findViewById(R.id.home_xlist_view);


        Bundle bundle = getArguments();
        title = bundle.getString(KEY_TITLE, "");
        url = bundle.getString(KEY_URL, "");
        url_footer = bundle.getString(KEY_URL_FOOTER, "");
        adapter = new MyHomeListViewAdapter(getActivity(), newData);
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
                PageIndex = 1;

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
                PageIndex++;

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
     //   Log.e("sadddddddddd", http_url);
        asyncHttpClient.get(getContext(), http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<HoltBean.DataBean> data = JsonUtil.getJson(responseString);

                newData.addAll(data);
                adapter.notifyDataSetChanged();


            }
        });
    }

    public String getTitle() {
        return title;
    }

}
