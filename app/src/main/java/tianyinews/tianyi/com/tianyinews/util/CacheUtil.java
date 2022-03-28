package tianyinews.tianyi.com.tianyinews.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.LogUtils;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/16.
 */

public class CacheUtil {
    public static void putBooleans(Context context, boolean flag) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_first", flag);
        editor.commit();
    }

    public static boolean getBooleans(Context context, boolean flag) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean is_first = preferences.getBoolean("is_first", flag);
        return is_first;
    }
}
