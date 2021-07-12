package tianyinews.tianyi.com.tianyinews.view

import android.content.Context
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import tianyinews.tianyi.com.tianyinews.bean.INavigatorEntityCreater
import tianyinews.tianyi.com.tianyinews.ext.titles.ColorFlipPagerTitleView
import tianyinews.tianyi.com.tianyinews.ext.titles.ScaleTransitionPagerTitleView

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 3:21 下午
 * @Description:
 */
class MBCommonNavigatorAdapter<T> constructor(
    private val dataList: MutableList<T>,
    val viewpager: ViewPager,
    val styleType: Int = 1,
    private val indicator: IPagerIndicator
) : CommonNavigatorAdapter() {

    companion object {
        const val TYPE_TITLE_SCALETRANSITION = 1
        const val TYPE_TITLE_COLORFLIP = 2


        const val TYPE_INDICATOR_LINE = 1


    }

    override fun getCount(): Int = dataList.size
    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        return when (styleType) {
            TYPE_TITLE_SCALETRANSITION -> {
                ScaleTransitionPagerTitleView(context).apply {
                    textSize = 14f
                    normalColor = Color.parseColor("#9e9e9e")
                    selectedColor = Color.parseColor("#D43D3D")
                    val t = dataList[index]
                    if (t is INavigatorEntityCreater) {
                        text = t.getTabTitle()
                        setOnClickListener {
                            viewpager.currentItem = index
                        }
                    }
                }
            }
            TYPE_TITLE_COLORFLIP -> {
                ColorFlipPagerTitleView(context).apply {
                    textSize = 14f
                    normalColor = Color.parseColor("#9e9e9e")
                    selectedColor = Color.parseColor("#D43D3D")
                    val t = dataList[index]
                    if (t is INavigatorEntityCreater) {
                        text = t.getTabTitle()
                        setOnClickListener {
                            viewpager.currentItem = index
                        }
                    }
                }
            }
            else -> {
                ScaleTransitionPagerTitleView(context).apply {
                    textSize = 14f
                    normalColor = Color.parseColor("#9e9e9e")
                    selectedColor = Color.parseColor("#D43D3D")
                    val t = dataList[index]
                    if (t is INavigatorEntityCreater) {
                        text = t.getTabTitle()
                        setOnClickListener {
                            viewpager.currentItem = index
                        }
                    }
                }
            }
        }
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        return indicator
    }
}