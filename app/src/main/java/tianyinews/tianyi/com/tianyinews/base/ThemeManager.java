package tianyinews.tianyi.com.tianyinews.base;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @类的用途: 主题管理类
 * @作者: 任正威
 * @date: 2017/3/16.
 */

public class ThemeManager {
    //默认是白天模式
    private static ThemeMode mThemeMode = ThemeMode.DAY;

    //主题模式监听器
    //LinkedList-->增删快
    private static List<OnThemeChangedListener> mThemeChangedListenerList = new LinkedList<>();
    //夜间资源的缓存 key ：资源类型 ,值<key ：资源名称, values : int值>
    private static HashMap<String, HashMap<String, Integer>> sCachedNightResroucse = new HashMap<>();
    //夜间资源的后缀
    private static final String RESOURCE_SUFFIX = "_night";

    //主题模式 日间和夜间  此处我们用枚举类定义
    public enum ThemeMode {
        DAY, NIGHT
    }

    /**
     * 设置主题模式
     *
     * @param
     */
    public static void setThemeMode(ThemeMode themeMode) {
        if (mThemeMode != themeMode) {
            mThemeMode = themeMode;
            if (mThemeChangedListenerList.size() > 0) {
                for (OnThemeChangedListener listener :
                        mThemeChangedListenerList) {
                    listener.onThemeChanged();
                }
            }
        }
    }

    /**
     * 根据传入的日间模式的resId得到相应主题的resId，注意：必须是日间模式的resId
     *
     * @return 相应主题的resId，若为日间模式，则得到dayResId；反之夜间模式得到nightResId
     */
    public static int getCurrentThemeRes(Context context, int dayResId) {
        //先判断
        if (getThemeMode() == ThemeMode.DAY) {
            return dayResId;
        }
        //获取资源名
        String entryName = context.getResources().getResourceEntryName(dayResId);
        //获取资源类型
        String typeName = context.getResources().getResourceTypeName(dayResId);
        //通过资源类型获取值
        HashMap<String, Integer> cachedRes = sCachedNightResroucse.get(typeName);
        if (cachedRes == null) {
            cachedRes = new HashMap<>();
        }

        // 先从缓存中去取，如果有直接返回该id
        Integer resId = cachedRes.get(entryName + RESOURCE_SUFFIX);
        if ( resId != null &&resId != 0) {
            return resId;
        } else {
            //如果缓存中没有再根据资源id去动态获取

            // 通过资源名，资源类型，包名得到资源int值
            int nightResId = context.getResources().getIdentifier(entryName + RESOURCE_SUFFIX, typeName, context.getPackageName());
            //放入缓存中
            cachedRes.put(entryName + RESOURCE_SUFFIX, nightResId);
            sCachedNightResroucse.put(typeName, cachedRes);
            return nightResId;
            
        }


    }

    //注册ThemeChangedListener
    public static void registerThemeChangeListener(OnThemeChangedListener listener) {
        //判断监听者是否存在
        if (!mThemeChangedListenerList.contains(listener)) {
            mThemeChangedListenerList.add(listener);
        }
    }

    //注销ThemeChangedListener
    public static void unregisterThemeChangeListener(OnThemeChangedListener listener) {
        //判断监听者是否存在
        if (mThemeChangedListenerList.contains(listener)) {
            mThemeChangedListenerList.remove(listener);
        }
    }

    //得到主题模式
    public static ThemeMode getThemeMode() {
        return mThemeMode;
    }


    //主题模式切换监听器
    public interface OnThemeChangedListener {

        //主题切换时回调
        void onThemeChanged();
    }
}
