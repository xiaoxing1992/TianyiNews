package tianyinews.tianyi.com.tianyinews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.VideoBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyVideoListViewAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    private static final String TAG = "MyHomeListViewAdapter";

    public MyVideoListViewAdapter() {
        super(R.layout.videochildfragment);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VideoBean videoBean) {
        helper.setText(R.id.tv_video_topicname,videoBean.topicName);
        helper.setText(R.id.tv_video_ptime,videoBean.ptime);
        JzvdStd jzvdStd = helper.getView(R.id.player_list_video);
        jzvdStd.setUp(videoBean.mp4_url, videoBean.title, Jzvd.SCREEN_NORMAL);
        Glide.with(jzvdStd.posterImageView.getContext()).load(videoBean.cover).into(jzvdStd.posterImageView);
    }
}
