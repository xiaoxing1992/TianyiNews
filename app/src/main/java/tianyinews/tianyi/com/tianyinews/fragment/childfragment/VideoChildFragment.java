package tianyinews.tianyi.com.tianyinews.fragment.childfragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.adapter.MyVideoListViewAdapter;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.VideoBean;
import tianyinews.tianyi.com.tianyinews.util.JsonUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class VideoChildFragment extends BaseFragment {
    public static final String KEY_URL = "key_url";
    public static final String KEY_URL_FOOTER = "key_url_footer";
    private int PageIndex = 0;
    private MyVideoListViewAdapter adapter;
    private String url;
    private String url_footer;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.homechildfragment;
    }

    @Override
    protected void initView(View view) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);

        Bundle bundle = getArguments();
        url = bundle.getString(KEY_URL, "");
        url_footer = bundle.getString(KEY_URL_FOOTER, "");
        adapter = new MyVideoListViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        //获取网络数据
        getServerData(true);
    }


    @Override
    protected void initListener() {
        super.initListener();
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            PageIndex = 0;
            getServerData(true);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            PageIndex++;
            getServerData(false);
        });
    }

    private void getServerData(boolean isRefresh) {
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
                if(isRefresh){
                    adapter.setList(videoJson);
                }else{
                    adapter.addData(videoJson);
                }
            }
        });
    }
}
