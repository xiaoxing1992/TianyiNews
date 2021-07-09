package tianyinews.tianyi.com.tianyinews.view

import android.content.Context
import android.graphics.Color
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import tianyinews.tianyi.com.tianyinews.R
import tianyinews.tianyi.com.tianyinews.bean.CategoryHotItem
import tianyinews.tianyi.com.tianyinews.bean.MyChannel
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 3:21 下午
 * @Description:
 */
class ZZCommonNavigatorAdapter constructor(val dataList: MutableList<CategoryHotItem>?,
                                           val viewpager: ViewPager) : CommonNavigatorAdapter() {


    override fun getCount(): Int = dataList?.size?:0
    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        return ScaleTransitionPagerTitleView(context).apply {
            textSize = 18f
            normalColor = Color.parseColor("#9e9e9e")
            selectedColor = Color.parseColor("#D43D3D")
            text = dataList?.get(index)?.name?:""
            setOnClickListener {
                viewpager.currentItem = index
            }
        }
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        val indicator = DotPagerIndicator(context)
        indicator.setRadius(SizeUtils.dp2px(3f).toFloat())
        indicator.setDotColor(ColorUtils.getColor(R.color.chinaHoliDay))
        return indicator
    }
}