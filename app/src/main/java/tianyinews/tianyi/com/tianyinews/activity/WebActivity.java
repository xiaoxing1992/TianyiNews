package tianyinews.tianyi.com.tianyinews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.util.JsonUtil;

public class WebActivity extends AppCompatActivity {

    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String newUrl = intent.getStringExtra("newUrl");
        web_view = (WebView) findViewById(R.id.web_view);

        //设置WebView的一些缩放功能点
        web_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web_view.setHorizontalScrollBarEnabled(false);
        web_view.getSettings().setSupportZoom(true);
        //  web_view.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);


        //设置WebView可触摸放大缩小
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.setInitialScale(70);
        web_view.setHorizontalScrollbarOverlay(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        web_view.getSettings().setUseWideViewPort(true);
        //提高渲染的优先级
        //  web_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //把图片加载放在最后来加载渲染
        //webView.getSettings().setBlockNetworkImage(true);
        //用WebView将字符串以HTML的形式显示出来
        //webView.loadDataWithBaseURL("fake://not/needed", <p>zzz</p>, "text/html", "utf-8", "");
        //在同种分辨率的情况下,屏幕密度不一样的情况下,自动适配页面:
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取当前界面的高度
        //int width = dm.widthPixels;
        //int height = dm.heightPixels;
        int scale = dm.densityDpi;
        if (scale == 240) {
            web_view.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (scale == 160) {
            web_view.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else {
            web_view.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }


        web_view.loadUrl(newUrl);
        web_view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // webview自己加载URL，让后通知系统不需要HandleURL
                view.loadUrl(url);
                return true;
            }
        });
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        web_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {  //表示按返回键

                        web_view.goBack();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }


}
