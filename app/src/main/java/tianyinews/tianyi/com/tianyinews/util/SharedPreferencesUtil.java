package tianyinews.tianyi.com.tianyinews.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */

public class SharedPreferencesUtil {
    public static void putSharedConfig(Context context, boolean isLoginQQ) {
        SharedPreferences preferences = context.getSharedPreferences("qqconfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoginQQ", isLoginQQ);
        editor.commit();
    }

    //存储不同的标识
    public static void putSharedFlag(Context context, int flagInt) {
        SharedPreferences preferences = context.getSharedPreferences("login_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("isFlagInt", flagInt);
        editor.commit();
    }

    public static boolean getSharedConfig(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("qqconfig", Context.MODE_PRIVATE);
        boolean isLoginQQ = preferences.getBoolean("isLoginQQ", false);
        return isLoginQQ;
    }

    public static int getSharedFlag(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("login_config", Context.MODE_PRIVATE);
        int isFlagInt = preferences.getInt("isFlagInt", 0);
        return isFlagInt;
    }
}
