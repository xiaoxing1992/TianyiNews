package tianyinews.tianyi.com.tianyinews.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.bean.NewsBean;
import tianyinews.tianyi.com.tianyinews.bean.NewsMultiItemEntity;

import static tianyinews.tianyi.com.tianyinews.bean.NewsMultiItemEntity.IMG_TWO;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyHomeListViewAdapter extends BaseMultiItemQuickAdapter<NewsBean.ResultBean.DataBean, BaseViewHolder> {
    private static final String TAG = "MyHomeListViewAdapter";

    public MyHomeListViewAdapter(){
        addItemType(NewsMultiItemEntity.TEXT,R.layout.hometype_one);
        addItemType(NewsMultiItemEntity.IMG,R.layout.hometype_one);
        addItemType(NewsMultiItemEntity.IMG_TWO,R.layout.hometype_one_two);
        addItemType(NewsMultiItemEntity.IMG_THREE,R.layout.hometype_one_three);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NewsBean.ResultBean.DataBean item) {
        // 根据返回的 type 分别设置数据
        switch (helper.getItemViewType()) {
            case NewsMultiItemEntity.IMG:
                helper.setText(R.id.type_one_title, item.title);
                helper.setText(R.id.type_one_groupname, item.author_name);
                helper.setText(R.id.type_one_comments, item.category);
                break;
            case NewsMultiItemEntity.IMG_TWO:
                helper.setText(R.id.type_one_two_title, item.title);
                helper.setText(R.id.type_one_two_groupname, item.author_name);
                helper.setText(R.id.type_one_two_comments, item.category);
                ImageLoader.getInstance().displayImage(item.thumbnail_pic_s, (ImageView) helper.getView(R.id.type_one_two_img));
                break;
            case NewsMultiItemEntity.IMG_THREE:
                helper.setText(R.id.type_one_three_title, item.title);
                helper.setText(R.id.type_one_three_groupname, item.author_name);
                helper.setText(R.id.type_one_three_comments, item.category);
                ImageLoader.getInstance().displayImage(item.thumbnail_pic_s, (ImageView) helper.getView(R.id.type_one_three_img));
                ImageLoader.getInstance().displayImage(item.thumbnail_pic_s02, (ImageView) helper.getView(R.id.type_one_three_img02));
                ImageLoader.getInstance().displayImage(item.thumbnail_pic_s03, (ImageView) helper.getView(R.id.type_one_three_img03));
                break;
            default:
                helper.setText(R.id.type_one_title, item.title);
                helper.setText(R.id.type_one_groupname, item.author_name);
                helper.setText(R.id.type_one_comments, item.category);
                break;
        }
    }
}
