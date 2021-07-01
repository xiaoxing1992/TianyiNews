package tianyinews.tianyi.com.tianyinews.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.activity.MainActivity;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;
import tianyinews.tianyi.com.tianyinews.bean.PhoneUserBean;
import tianyinews.tianyi.com.tianyinews.bean.UserBean;
import tianyinews.tianyi.com.tianyinews.db.UserDao;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */

public class CareFragment extends BaseFragment {

    private TextView settings_title_name;
    public static ImageView care_zhuantai_img;
    private MainActivity mainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.carefragment;
    }


    @Override
    protected void initView(View view) {
        settings_title_name = (TextView) view.findViewById(R.id.settings_title_name);
        care_zhuantai_img = (ImageView) view.findViewById(R.id.care_zhuantai_img);
        boolean config = SharedPreferencesUtil.getSharedConfig(mContext);
        mainActivity = (MainActivity) getActivity();

        if (config) {
            care_zhuantai_img.setVisibility(View.VISIBLE);
            care_zhuantai_img.setImageResource(R.drawable.nofavorite_loading);

        } else {
            care_zhuantai_img.setVisibility(View.VISIBLE);
            care_zhuantai_img.setImageResource(R.drawable.nologin_loading);
        }
    }



    @Override
    protected void initData() {
        super.initData();
        settings_title_name.setText("收藏");
        mainActivity.getSkin(new MainActivity.SkinOnListener() {
            @Override
            public void setSkin(int i) {
                if (i == 1000) {
                    care_zhuantai_img.setVisibility(View.VISIBLE);
                    care_zhuantai_img.setImageResource(R.drawable.nofavorite_loading);
                } else if (i == 1001) {
                    care_zhuantai_img.setVisibility(View.VISIBLE);
                    care_zhuantai_img.setImageResource(R.drawable.nologin_loading);
                } else if (i == 1002) {
                    care_zhuantai_img.setVisibility(View.VISIBLE);
                    care_zhuantai_img.setImageResource(R.drawable.nofavorite_loading);
                } else if (i == 1003) {
                    care_zhuantai_img.setVisibility(View.VISIBLE);
                    care_zhuantai_img.setImageResource(R.drawable.nologin_loading);
                }

            }
        });

    }

}
