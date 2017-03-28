package tianyinews.tianyi.com.tianyinews.fragment.childfragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import tianyinews.tianyi.com.tianyinews.activity.WebActivity;
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

                for (int i = 0; i < data.size(); i++) {
                    int id = data.get(i).id;
                    int categoryID = data.get(i).categoryID;
                    String newUrl = "http://sight.urundata.com:5004/v1.0.0/Article/GetArticleDetail?CategoryID=" + categoryID + "&UserID=864394010080028&ArticleID=" + id + "&ArticleType=0";
                    data.get(i).newUrl = newUrl;
                }
                newData.addAll(data);
                adapter.notifyDataSetChanged();


            }
        });
        home_xlist_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String newUrl = newData.get(i - 1).newUrl;
                //    Log.e("Ssssssss",newUrl);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("newUrl", newUrl);
                startActivity(intent);
            }
        });

        home_xlist_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("收藏精彩看点");
                builder.setMessage("确定收藏吗？收藏以后更方便阅读精彩内容");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //收藏状态是在已经登录的情况下才能收藏
                        //收藏到数据库并将关注页面状态更改
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public String getTitle() {
        return title;
    }

}
