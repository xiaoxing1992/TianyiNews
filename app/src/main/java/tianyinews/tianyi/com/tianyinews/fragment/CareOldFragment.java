package tianyinews.tianyi.com.tianyinews.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.activity.MainActivity;
import tianyinews.tianyi.com.tianyinews.base.BaseOldFragment;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */

public class CareOldFragment extends BaseOldFragment {

    private TextView settings_title_name;
    public static ImageView care_zhuantai_img;

    @Override
    protected int getLayoutId() {
        return R.layout.carefragment;
    }


    @Override
    protected void initView(View view) {
        settings_title_name = (TextView) view.findViewById(R.id.settings_title_name);
        care_zhuantai_img = (ImageView) view.findViewById(R.id.care_zhuantai_img);
    }



    @Override
    protected void initData() {
        super.initData();
        settings_title_name.setText("收藏");
    }

}
