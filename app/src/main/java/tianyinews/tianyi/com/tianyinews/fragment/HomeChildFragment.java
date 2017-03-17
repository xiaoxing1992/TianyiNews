package tianyinews.tianyi.com.tianyinews.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.weiwei.xlistviewlibrary.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class HomeChildFragment extends BaseFragment {

    private XListView home_xlist_view;
    private Handler handler = new Handler();

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.homechildfragment, null);
        home_xlist_view = (XListView) view.findViewById(R.id.home_xlist_view);

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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_xlist_view.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_xlist_view.stopLoadMore();
                    }
                }, 2000);
            }
        });
        //获取网络数据
        getServerData();

    }

    private void getServerData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String http_url = "http://c.m.163.com/nc/article/list/T1467284926140/0-20.html";
        asyncHttpClient.get(getContext(), http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject obj = new JSONObject(responseString);
                    Iterator<String> keys = obj.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        JSONArray jsonArray = obj.optJSONArray(next);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
