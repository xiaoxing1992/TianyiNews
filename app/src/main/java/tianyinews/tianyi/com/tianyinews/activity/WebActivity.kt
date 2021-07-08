package tianyinews.tianyi.com.tianyinews.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gyf.immersionbar.ImmersionBar
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.base.BaseActivity
import tianyinews.tianyi.com.tianyinews.databinding.ActivityWebBinding
import tianyinews.tianyi.com.tianyinews.viewmodel.WebViewModel

class WebActivity : BaseActivity<WebViewModel,ActivityWebBinding>() {

    override fun layoutId(): Int = R.layout.activity_web

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init()


        val newUrl = intent.getStringExtra("newUrl")

        //设置WebView的一些缩放功能点
        mDatabind.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        mDatabind.webView.isHorizontalScrollBarEnabled = false
        mDatabind.webView.settings.setSupportZoom(true)
        //  web_view.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);
        //设置WebView可触摸放大缩小
        mDatabind.webView.settings.builtInZoomControls = true
        mDatabind.webView.setInitialScale(70)
        mDatabind.webView.setHorizontalScrollbarOverlay(true)
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        mDatabind.webView.settings.useWideViewPort = true
        //提高渲染的优先级
        //  web_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //把图片加载放在最后来加载渲染
        //webView.getSettings().setBlockNetworkImage(true);
        //用WebView将字符串以HTML的形式显示出来
        //webView.loadDataWithBaseURL("fake://not/needed", <p>zzz</p>, "text/html", "utf-8", "");
        //在同种分辨率的情况下,屏幕密度不一样的情况下,自动适配页面:
        val dm = resources.displayMetrics
        // 获取当前界面的高度
        //int width = dm.widthPixels;
        //int height = dm.heightPixels;
        val scale = dm.densityDpi
        if (scale == 240) {
            mDatabind.webView.settings.defaultZoom = WebSettings.ZoomDensity.FAR
        } else if (scale == 160) {
            mDatabind.webView.settings.defaultZoom = WebSettings.ZoomDensity.MEDIUM
        } else {
            mDatabind.webView.settings.defaultZoom = WebSettings.ZoomDensity.CLOSE
        }
        mDatabind.webView.loadUrl(newUrl!!)
        mDatabind.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // webview自己加载URL，让后通知系统不需要HandleURL
                view.loadUrl(url)
                return true
            }
        }
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        mDatabind.webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&mDatabind.webView.canGoBack()) {  //表示按返回键
                    mDatabind.webView.goBack() //后退
                    //webview.goForward();//前进
                    return@OnKeyListener true //已处理
                }
            }
            false
        })
    }

    companion object{
        @JvmStatic
        fun start(context: Context,newUrl:String) {
            val starter = Intent(context, WebActivity::class.java)
            starter.putExtra("newUrl", newUrl)
            context.startActivity(starter)
        }
    }
}