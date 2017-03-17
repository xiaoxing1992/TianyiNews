package tianyinews.tianyi.com.tianyinews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.base.BaseFragment;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/17.
 */

public class LeftFragment extends Fragment {
    private Object views;
    private View view;
    private RelativeLayout left_mian_rl;
    private TextView header_tv;
    private RelativeLayout ll_content;
    private LinearLayout nights_ll;
    private TextView settrings_tv;
    private RelativeLayout search_btn;
    private RelativeLayout message_btn;
    private RelativeLayout offline_btn;
    private RelativeLayout app_activity_btn;
    private RelativeLayout feedback_btn;
    private RelativeLayout appstore_btn;
    private TextView message_text;
    private TextView offline_btn_text;
    private TextView app_activity_text;
    private TextView feedback_text;
    private TextView appstore_text;
    private TextView calls_tv;
    private TextView nights_tv;
    private TextView search_btn_text;
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.left_layout, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
    }

    public void getViews() {
        nights_ll = (LinearLayout) view.findViewById(R.id.nights_ll);
        /*
        left_mian_rl = (RelativeLayout) view.findViewById(R.id.left_mian_rl);
        header_tv = (TextView) view.findViewById(R.id.header_tv);
        ll_content = (RelativeLayout) view.findViewById(R.id.ll_content);
        settrings_tv = (TextView) view.findViewById(R.id.settrings_tv);
        //  slidingMenu.findViewById(R.id.rb_night);
        search_btn = (RelativeLayout) view.findViewById(R.id.search_btn);
        message_btn = (RelativeLayout) view.findViewById(R.id.message_btn);
        offline_btn = (RelativeLayout) view.findViewById(R.id.offline_btn);
        app_activity_btn = (RelativeLayout) view.findViewById(R.id.app_activity_btn);
        feedback_btn = (RelativeLayout) view.findViewById(R.id.feedback_btn);
        appstore_btn = (RelativeLayout) view.findViewById(R.id.appstore_btn);
        calls_tv = (TextView) view.findViewById(R.id.calls_tv);*/
        // nights_tv = (TextView) view.findViewById(R.id.nights_tv);
        /*search_btn_text = (TextView) view.findViewById(R.id.search_btn_text);

        settrings_tv = (TextView) view.findViewById(R.id.settrings_tv);
        message_text = (TextView) view.findViewById(R.id.message_text);
        offline_btn_text = (TextView) view.findViewById(R.id.offline_btn_text);
        app_activity_text = (TextView) view.findViewById(R.id.app_activity_text);
        feedback_text = (TextView) view.findViewById(R.id.feedback_text);
        appstore_text = (TextView) view.findViewById(R.id.appstore_text);*/
        nights_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    flag = true;
                    ChangeModeController.changeDay(getActivity(), R.style.DayTheme);
                } else {
                    flag = false;
                    ChangeModeController.changeNight(getActivity(), R.style.NightTheme);
                }

               /* attrTypedValue = ChangeModeController.getAttrTypedValue(this, R.attr.zztextColor);
                toolbar.setTitleTextColor(getResources().getColor(attrTypedValue.resourceId));*/

                /*attrTypedValue = ChangeModeController.getAttrTypedValue(this, R.attr.zztextColor);
                toolbar.setTitleTextColor(getResources().getColor(attrTypedValue.resourceId));*/
            }
        });

    }
}
