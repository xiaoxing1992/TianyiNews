package tianyinews.tianyi.com.tianyinews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import thinkfreely.changemodelibrary.ChangeModeController;
import tianyinews.tianyi.com.tianyinews.R;

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
   /* public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ};*/
    private boolean flag = false;
    private boolean isauth;
    private ImageView qq_login_img;
    private RelativeLayout qq_user_login_jicheng;
    private RelativeLayout qq_user_rl;
    private ImageView qq_user_img;
    private TextView qq_user_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.left_layout, container, false);
        nights_ll = (LinearLayout) view.findViewById(R.id.nights_ll);
      /*  qq_login_img = (ImageView) view.findViewById(R.id.qq_login_img);
        qq_user_login_jicheng = (RelativeLayout) view.findViewById(R.id.qq_user_login_jicheng);
        qq_user_rl = (RelativeLayout) view.findViewById(R.id.qq_user_rl);
        qq_user_img = (ImageView) view.findViewById(R.id.qq_user_img);
        qq_user_name = (TextView) view.findViewById(R.id.qq_user_name);*/
     //   initPlatforms();
       /* isauth = UMShareAPI.get(getActivity()).isAuthorize(getActivity(), platforms.get(0).mPlatform);*/

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
    }

  /*  private void initPlatforms() {
        platforms.clear();
        for (SHARE_MEDIA e : list) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())) {
                platforms.add(e.toSnsPlatform());
            }
        }
    }*/

    public void getViews() {


        /*qq_login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UMShareAPI.get(getActivity()).doOauthVerify(getActivity(), platforms.get(0).mPlatform, authListener);
            }
        });*/



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

   /* UMAuthListener authListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();

            switch (action) {
                case ACTION_AUTHORIZE:
                    UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), platforms.get(0).mPlatform, authListener);
                    break;
                case ACTION_DELETE:
                    break;
                case ACTION_GET_PROFILE:


                    qq_user_login_jicheng.setVisibility(View.GONE);
                    qq_user_rl.setVisibility(View.VISIBLE);
                    String name = data.get("screen_name");
                    qq_user_name.setText(name);
                   *//* String gender = data.get("gender");
                    tvvv_tv.setText(gender);*//*
                    String iconurl = data.get("iconurl");
                    ImageOptions options = new ImageOptions.Builder()
                            .setCircular(true)
                            .setSize(100, 100)
                            .setLoadingDrawableId(R.mipmap.ic_launcher)
                            .build();
                    x.image().bind(qq_user_img, iconurl, options);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getActivity(), "请求取消", Toast.LENGTH_SHORT).show();
        }
    };*/

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(getActivity()).release();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(getActivity()).onSaveInstanceState(outState);
    }*/
}
