package tianyinews.tianyi.com.tianyinews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.VideoBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyVideoListViewAdapter extends BaseAdapter {
    private static final String TAG = "MyHomeListViewAdapter";

    Context context;
    List<VideoBean> videoBeenList;

    public MyVideoListViewAdapter(Context context, List<VideoBean> videoBeenList) {
        this.context = context;
        this.videoBeenList = videoBeenList;
    }


    @Override
    public int getCount() {
        return videoBeenList.size();
    }

    @Override
    public Object getItem(int i) {
        return videoBeenList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderOne viewHolderOne = null;
        if (view == null) {
            viewHolderOne = new ViewHolderOne();
            view = View.inflate(context, R.layout.videochildfragment, null);
            viewHolderOne.player_list_video = (JZVideoPlayerStandard) view.findViewById(R.id.player_list_video);
            viewHolderOne.img_video_title = (TextView) view.findViewById(R.id.img_video_title);
            viewHolderOne.tv_video_topicname = (TextView) view.findViewById(R.id.tv_video_topicname);
            viewHolderOne.tv_video_ptime = (TextView) view.findViewById(R.id.tv_video_ptime);

            view.setTag(viewHolderOne);

        } else {
            viewHolderOne = (ViewHolderOne) view.getTag();

        }
        //  viewHolderOne.img_video_title.setText(videoBeenList.get(i).title);
        viewHolderOne.tv_video_topicname.setText(videoBeenList.get(i).topicName);
        viewHolderOne.tv_video_ptime.setText(videoBeenList.get(i).ptime);


        viewHolderOne.player_list_video.setUp(videoBeenList.get(i).mp4_url
                , JZVideoPlayerStandard.SCREEN_WINDOW_LIST, "");
        Glide.with(viewHolderOne.player_list_video.thumbImageView.getContext()).load(videoBeenList.get(i).cover).into(viewHolderOne.player_list_video.thumbImageView);

        return view;
    }


    class ViewHolderOne {
        JZVideoPlayerStandard player_list_video;
        TextView img_video_title;
        TextView tv_video_topicname;
        TextView tv_video_ptime;
    }

}
