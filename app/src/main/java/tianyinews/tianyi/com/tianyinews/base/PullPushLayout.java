package tianyinews.tianyi.com.tianyinews.base;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;

import tianyinews.tianyi.com.tianyinews.R;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/15.
 */
public class PullPushLayout extends ScrollView {

    public interface OnTouchEventMoveListenre {

        public void onSlideUp(int mOriginalHeaderHeight, int mHeaderHeight);

        public void onSlideDwon(int mOriginalHeaderHeight, int mHeaderHeight);

        public void onSlide(int alpha);
    }


    private OnTouchEventMoveListenre mOnTouchEventMoveListenre;

    public void setOnTouchEventMoveListenre(OnTouchEventMoveListenre l) {
        mOnTouchEventMoveListenre = l;
    }

    private int mAlpha = 0;  //透明度
    private static final float MAX_ALPHA = 255.00000f;

    //header的高度   单位是 px
    private int mHeaderHeight;//实时高度
    private int lastTranslationY;//上次header所在的TranslationY
    private int deltaTranslationY; //header的高度增量

    private ViewGroup mHeader;
    private View mHeaderChild;
    private View mContent;
    private ObjectAnimator oa;
    private float lastY = -1;
    private float deltaY = -1;


    private int range; //header滑动范围的最大值

    public PullPushLayout(Context context) {
        super(context);
    }

    public PullPushLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullPushLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setVerticalScrollBarEnabled(false);
        initView();
    }


    private void initView() {
        mHeader = (ViewGroup) findViewById(R.id.rl_top);
        mHeaderChild = mHeader.getChildAt(0);
        mContent = findViewById(R.id.ll_content);

        mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                range = mHeader.getHeight();
                mHeader.getLayoutParams().height = range;
                mHeaderHeight = range;
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && (mHeader == null || mContent == null)) {

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() != 0) {
                    deltaY = 0;
                    lastY = ev.getY();
                } else {
                    deltaY = ev.getY() - lastY;
                    if (deltaY > 0) {
                        //下滑
                        setT((int) -deltaY / 5);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() < range) {
                    if (deltaY != 0) {
                        //还原
                        reset();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 监听滚动事件的相关逻辑
     */
    private void scrollListen(float percent) {
        mHeaderHeight -= deltaTranslationY;

        if (mOnTouchEventMoveListenre != null) {

            mAlpha = (int) (percent * MAX_ALPHA);

            if (deltaTranslationY < 0) {
                // 下滑
                mOnTouchEventMoveListenre.onSlideDwon(
                        range, mHeaderHeight);
            } else if (deltaTranslationY > 0) {
                // 上滑
                mOnTouchEventMoveListenre.onSlideUp(
                        range, mHeaderHeight);
            }

            if (mHeaderHeight == range) {
                mAlpha = 0;
            }
            if (mAlpha > 255) {
                mAlpha = 255;
            }
            if (mAlpha < 0) {
                mAlpha = 0;
            }

            mOnTouchEventMoveListenre.onSlide(mAlpha);

        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > range) {
            return;
        }
        float percent = animateScroll(t);

        animateUpSlide(t); //动态改变header的高度
        deltaTranslationY = t - lastTranslationY;
        lastTranslationY = t;

        scrollListen(percent);
    }


    /**
     * 下拉超出Header高度时Header放大效果
     * @param t
     */
    public void setT(int t) {
        scrollTo(0, t);
        if (t < 0) {
            animatePull(t);
        }
    }

    /**
     * 上滑时Header view被吃掉的效果
     * @param t
     * @return header增量部分占header高度的百分比
     */
    private float animateScroll(int t) {
        float percent = (float) t / range;
        if (percent > 1) {
            percent = 1;
        }
        ViewHelper.setTranslationY(mHeader, t);

        return percent;

    }
    //下拉时Header view放大的效果
    private void animatePull(int t) {
        Message msg = mAnimatePullHandler.obtainMessage();
        msg.arg1 = t;
        mAnimatePullHandler.sendMessage(msg);
    }
    //下拉的处理逻辑,放在函数中在某些机型上GPU会处理不过来造成重绘失败,所以要放在handler中处理
    private Handler mAnimatePullHandler = new Handler() {
        public void handleMessage(Message msg) {
            int t = msg.arg1;
            mHeader.getLayoutParams().height = range - t;
            mHeaderChild.getLayoutParams().height = range - t;
            mHeaderChild.requestLayout();
        }

        ;

    };
    //Header高度范围内滑动时的效果
    private void animateUpSlide(int t) {

        Message msg = mAnimateUpSlideHandler.obtainMessage();
        msg.arg1 = t;
        mAnimateUpSlideHandler.sendMessage(msg);
    }
    //上滑的处理逻辑,放在函数中在某些机型上GPU会处理不过来造成重绘失败,所以要放在handler中处理
    private Handler mAnimateUpSlideHandler = new Handler() {
        public void handleMessage(Message msg) {
            int t = msg.arg1;
            mHeaderChild.getLayoutParams().height = range - t;
            mHeaderChild.requestLayout();
        }

        ;

    };
    /*
     * 还原下拉超出原始高度的效果
     */
    private void reset() {
        if (oa != null && oa.isRunning()) {
            return;
        }
        oa = ObjectAnimator.ofInt(this, "t", (int) -deltaY / 5, 0);
        oa.setDuration(150);
        oa.start();
    }


}
