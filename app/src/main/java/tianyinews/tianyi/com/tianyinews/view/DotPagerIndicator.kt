package tianyinews.tianyi.com.tianyinews.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 4:34 下午
 * @Description:
 */
class DotPagerIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), IPagerIndicator {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDataList: MutableList<PositionData>? = null
    private var mCircleCenterX: Float = 0f
    private var mYOffset: Float = 0f
    private var mRadius: Float = 8f
    private var mDotColor: Int = Color.WHITE
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (mDataList.isNullOrEmpty()) return
        val data = mDataList!![position]
        mCircleCenterX = data.mLeft + data.width() / 2f
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint.color = mDotColor
        canvas?.drawCircle(mCircleCenterX, height - mYOffset - mRadius, mRadius, mPaint)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPositionDataProvide(dataList: MutableList<PositionData>?) {
        mDataList = dataList
    }


    fun getYOffset(): Float {
        return mYOffset
    }

    fun setYOffset(yOffset: Float) {
        mYOffset = yOffset
        invalidate()
    }

    fun getRadius(): Float {
        return mRadius
    }

    fun setRadius(radius: Float) {
        mRadius = radius
        invalidate()
    }

    fun getDotColor(): Int {
        return mDotColor
    }

    fun setDotColor(color: Int) {
        mDotColor = color
        invalidate()
    }

}