package tianyinews.tianyi.com.tianyinews.view

import android.content.Context
import android.graphics.Color
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 3:21 下午
 * @Description:
 */
class MBCommonNavigatorAdapter constructor(val context: Context, val dataList: MutableList<MyChannel>, val viewpager: ViewPager) : CommonNavigatorAdapter() {


    override fun getCount(): Int = dataList.size
    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        return ScaleTransitionPagerTitleView(context).apply {
            textSize = 18f
            normalColor = Color.parseColor("#9e9e9e")
            selectedColor = Color.parseColor("#D43D3D")
            text = dataList[index].title
            setOnClickListener {
                viewpager.currentItem = index
            }
        }
    }

    override fun getIndicator(context: Context?): IPagerIndicator {
        return LinePagerIndicator(context).apply {
            mode = LinePagerIndicator.MODE_EXACTLY
            lineHeight = UIUtil.dip2px(context, 6.0).toFloat()
            lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
            roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
            startInterpolator = AccelerateInterpolator()
            endInterpolator = DecelerateInterpolator(2.0f)
            setColors(Color.parseColor("#D43D3D"))
        }
    }
}