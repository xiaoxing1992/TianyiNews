package tianyinews.tianyi.com.tianyinews.fragment.childfragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import tianyinews.tianyi.com.tianyinews.activity.WebActivity;
import tianyinews.tianyi.com.tianyinews.adapter.MyHomeListViewAdapter;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.MyChannel;
import tianyinews.tianyi.com.tianyinews.bean.NewsBean;
import tianyinews.tianyi.com.tianyinews.util.JsonUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/14.
 */

public class HomeChildFragment extends BaseFragment {
    public static final java.lang.String MODEL_KEY = "model_key";
    private int PageIndex = 1;
    private MyHomeListViewAdapter adapter;
    private MyChannel mChannel;
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
        mChannel = (MyChannel) bundle.getSerializable(MODEL_KEY);
        adapter = new MyHomeListViewAdapter();
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PageIndex = 1;
                getServerData(true);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PageIndex++;
                getServerData(false);
            }
        });

        adapter.setOnItemClickListener((ada, view, position) -> {
            NewsBean.ResultBean.DataBean item = adapter.getItem(position);
            if(item==null)return;
            String newUrl = item.url;
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("newUrl", newUrl);
            startActivity(intent);
        });

        adapter.setOnItemLongClickListener((adapter, view, position) -> {
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
            builder.setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();

            return false  ;
        });
    }

    private void getServerData(boolean isResresh) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String http_url = mChannel.url + "?" + "type=" + mChannel.requestTitle + "&" + "page=" + PageIndex + "&" + mChannel.url_footer;
        //   Log.e("sadddddddddd", http_url);
        asyncHttpClient.get(getContext(), http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String url = "";
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<NewsBean.ResultBean.DataBean> data = JsonUtil.getJson(responseString);
                if(isResresh){
                    adapter.setList(data);
                    refreshLayout.finishRefresh();
                }else{
                    adapter.addData(data);
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    public String getTitle() {
        return mChannel.title;
    }

}
