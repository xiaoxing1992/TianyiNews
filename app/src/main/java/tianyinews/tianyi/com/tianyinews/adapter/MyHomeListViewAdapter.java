package tianyinews.tianyi.com.tianyinews.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.bean.NewsBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyHomeListViewAdapter extends BaseAdapter {
    private static final String TAG = "MyHomeListViewAdapter";

    Context context;
    List<NewsBean.ResultBean.DataBean> holtBeanList;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

    public MyHomeListViewAdapter(Context context, List<NewsBean.ResultBean.DataBean> holtBeanList) {
        this.context = context;
        this.holtBeanList = holtBeanList;
    }


    @Override

    public int getItemViewType(int position) {
        String imageUrl = holtBeanList.get(position).thumbnail_pic_s;
        String imageUrl2 = holtBeanList.get(position).thumbnail_pic_s02;
        String imageUrl3 = holtBeanList.get(position).thumbnail_pic_s03;
        List<String> urls = new ArrayList<>();
        if (!TextUtils.isEmpty(imageUrl)) {
            urls.add(imageUrl);
        }
        if (!TextUtils.isEmpty(imageUrl2)) {
            urls.add(imageUrl2);
        }
        if (!TextUtils.isEmpty(imageUrl3)) {
            urls.add(imageUrl3);
        }
        if (urls.size() <= 0) {
            return TYPE_TEXT;
        } else if (urls.size() == 1) {
            return TYPE_ONE;
        } else if (urls.size() == 2) {
            return TYPE_TWO;
        } else if (urls.size() >= 3) {
            return TYPE_THREE;
        } else {
            return TYPE_TEXT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getCount() {
        return holtBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return holtBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderText viewHoldertext = null;
        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case TYPE_TEXT:
                    viewHoldertext = new ViewHolderText();
                    view = View.inflate(context, R.layout.hometype_one, null);
                    viewHoldertext.type_one_title = (TextView) view.findViewById(R.id.type_one_title);
                    viewHoldertext.type_one_groupname = (TextView) view.findViewById(R.id.type_one_groupname);
                    viewHoldertext.type_one_comments = (TextView) view.findViewById(R.id.type_one_comments);
                    view.setTag(viewHoldertext);

                    break;
                case TYPE_ONE:
                    viewHolderOne = new ViewHolderOne();
                    view = View.inflate(context, R.layout.hometype_one, null);
                    viewHolderOne.type_one_title = (TextView) view.findViewById(R.id.type_one_title);
                    viewHolderOne.type_one_groupname = (TextView) view.findViewById(R.id.type_one_groupname);
                    viewHolderOne.type_one_comments = (TextView) view.findViewById(R.id.type_one_comments);
                    view.setTag(viewHolderOne);

                    break;
                case TYPE_TWO:
                    viewHolderTwo = new ViewHolderTwo();
                    view = View.inflate(context, R.layout.hometype_one_two, null);
                    viewHolderTwo.type_one_two_img = (ImageView) view.findViewById(R.id.type_one_two_img);
                    viewHolderTwo.type_one_two_title = (TextView) view.findViewById(R.id.type_one_two_title);
                    viewHolderTwo.type_one_two_groupname = (TextView) view.findViewById(R.id.type_one_two_groupname);
                    viewHolderTwo.type_one_two_comments = (TextView) view.findViewById(R.id.type_one_two_comments);
                    view.setTag(viewHolderTwo);
                    break;
                case TYPE_THREE:
                    viewHolderThree = new ViewHolderThree();
                    view = View.inflate(context, R.layout.hometype_one_three, null);
                    viewHolderThree.type_one_three_title = (TextView) view.findViewById(R.id.type_one_three_title);
                    viewHolderThree.type_one_three_img = (ImageView) view.findViewById(R.id.type_one_three_img);
                    viewHolderThree.type_one_three_img02 = (ImageView) view.findViewById(R.id.type_one_three_img02);
                    viewHolderThree.type_one_three_img03 = (ImageView) view.findViewById(R.id.type_one_three_img03);
                    viewHolderThree.type_one_three_groupname = (TextView) view.findViewById(R.id.type_one_three_groupname);
                    viewHolderThree.type_one_three_comments = (TextView) view.findViewById(R.id.type_one_three_comments);
                    view.setTag(viewHolderThree);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_ONE:
                    viewHolderOne = (ViewHolderOne) view.getTag();

                    break;
                case TYPE_TEXT:
                    viewHoldertext = (ViewHolderText) view.getTag();

                    break;
                case TYPE_TWO:
                    viewHolderTwo = (ViewHolderTwo) view.getTag();
                    break;
                case TYPE_THREE:
                    viewHolderThree = (ViewHolderThree) view.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_TEXT:
                viewHoldertext.type_one_title.setText(holtBeanList.get(i).title);
                viewHoldertext.type_one_groupname.setText(holtBeanList.get(i).author_name);
                viewHoldertext.type_one_comments.setText(holtBeanList.get(i).category);
                break;
            case TYPE_ONE:
                viewHolderOne.type_one_title.setText(holtBeanList.get(i).title);
                viewHolderOne.type_one_groupname.setText(holtBeanList.get(i).author_name);
                viewHolderOne.type_one_comments.setText(holtBeanList.get(i).category);
                break;
            case TYPE_TWO:

                try {
                    String imageUrl = holtBeanList.get(i).thumbnail_pic_s02;
                    ImageLoader.getInstance().displayImage(imageUrl, viewHolderTwo.type_one_two_img);
                    viewHolderTwo.type_one_two_title.setText(holtBeanList.get(i).title);
                    viewHolderTwo.type_one_two_groupname.setText(holtBeanList.get(i).author_name);
                    viewHolderTwo.type_one_two_comments.setText(holtBeanList.get(i).category);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case TYPE_THREE:

                try {
                    viewHolderThree.type_one_three_title.setText(holtBeanList.get(i).title);
                    String imageUrls = holtBeanList.get(i).thumbnail_pic_s;
                    String imageUrls2 = holtBeanList.get(i).thumbnail_pic_s02;
                    String imageUrls3 = holtBeanList.get(i).thumbnail_pic_s03;
                    ImageLoader.getInstance().displayImage(imageUrls, viewHolderThree.type_one_three_img);
                    ImageLoader.getInstance().displayImage(imageUrls2, viewHolderThree.type_one_three_img02);
                    ImageLoader.getInstance().displayImage(imageUrls3, viewHolderThree.type_one_three_img03);
                    viewHolderThree.type_one_three_groupname.setText(holtBeanList.get(i).author_name);
                    viewHolderThree.type_one_three_comments.setText(holtBeanList.get(i).category);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
        return view;
    }


    class ViewHolderText {
        TextView type_one_title;
        TextView type_one_groupname;
        TextView type_one_comments;
    }


    class ViewHolderOne {
        TextView type_one_title;
        TextView type_one_groupname;
        TextView type_one_comments;
    }


    class ViewHolderTwo {
        ImageView type_one_two_img;
        TextView type_one_two_title;
        TextView type_one_two_groupname;
        TextView type_one_two_comments;
    }

    class ViewHolderThree {
        TextView type_one_three_title;
        ImageView type_one_three_img;
        ImageView type_one_three_img02;
        ImageView type_one_three_img03;
        TextView type_one_three_groupname;
        TextView type_one_three_comments;


    }
}
