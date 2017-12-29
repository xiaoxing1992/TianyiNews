package tianyinews.tianyi.com.tianyinews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class MyHomeListViewAdapter extends BaseAdapter {
    private static final String TAG = "MyHomeListViewAdapter";

    Context context;
    List<HoltBean.DataBean> holtBeanList;
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    public static final int TYPE_THREE = 2;

    public MyHomeListViewAdapter(Context context, List<HoltBean.DataBean> holtBeanList) {
        this.context = context;
        this.holtBeanList = holtBeanList;
    }


    @Override

    public int getItemViewType(int position) {
        String imageUrl = holtBeanList.get(position).thumbnailImage;
        if (imageUrl == null) {
            return TYPE_ONE;
        } else {
            String[] urls = imageUrl.split("\\|");
            int length = urls.length;
            if (length >= 1 && length < 3) {
                return TYPE_TWO;
            } else {
                return TYPE_THREE;


            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
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
        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
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
                case TYPE_TWO:
                    viewHolderTwo = (ViewHolderTwo) view.getTag();
                    break;
                case TYPE_THREE:
                    viewHolderThree = (ViewHolderThree) view.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_ONE:
                viewHolderOne.type_one_title.setText(holtBeanList.get(i).title);
                viewHolderOne.type_one_groupname.setText(holtBeanList.get(i).siteName);
                viewHolderOne.type_one_comments.setText(holtBeanList.get(i).comments + "评论");
                break;
            case TYPE_TWO:

                try {
                    String imageUrl = holtBeanList.get(i).thumbnailImage;
                    String[] urls = imageUrl.split("\\|");
                /*for (int j = 0; j < urls.length; j++) {
                    Log.e("ssssssssss", urls[j]);
                }*/
                    ImageLoader.getInstance().displayImage(urls[0], viewHolderTwo.type_one_two_img);
                    viewHolderTwo.type_one_two_title.setText(holtBeanList.get(i).title);
                    viewHolderTwo.type_one_two_groupname.setText(holtBeanList.get(i).siteName);
                    viewHolderTwo.type_one_two_comments.setText(holtBeanList.get(i).comments + "评论");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case TYPE_THREE:

                try {
                    viewHolderThree.type_one_three_title.setText(holtBeanList.get(i).title);
                    String imageUrls = holtBeanList.get(i).thumbnailImage;
                    String[] urlss = imageUrls.split("\\|");
                    ImageLoader.getInstance().displayImage(urlss[0], viewHolderThree.type_one_three_img);
                    ImageLoader.getInstance().displayImage(urlss[1], viewHolderThree.type_one_three_img02);
                    ImageLoader.getInstance().displayImage(urlss[2], viewHolderThree.type_one_three_img03);
                    viewHolderThree.type_one_three_groupname.setText(holtBeanList.get(i).siteName);
                    viewHolderThree.type_one_three_comments.setText(holtBeanList.get(i).comments + "评论");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
        return view;
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
