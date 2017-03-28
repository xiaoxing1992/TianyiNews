package tianyinews.tianyi.com.tianyinews.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tianyinews.tianyi.com.tianyinews.bean.HoltBean;
import tianyinews.tianyi.com.tianyinews.bean.NewBean;
import tianyinews.tianyi.com.tianyinews.bean.VideoBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/21.
 */

public class JsonUtil {
    public static List<HoltBean.DataBean> getJson(String url) {
        Gson gson = new Gson();
        HoltBean holtBean = gson.fromJson(url, HoltBean.class);
        List<HoltBean.DataBean> data = holtBean.data;
        return data;
    }

    public static List<VideoBean> getVideoJson(String json) {
        Gson gson = new Gson();

        List<VideoBean> list = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String next = iterator.next();//每个key
                JSONArray jsonArray = jsonObject.optJSONArray(next);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.optJSONObject(i);

                    VideoBean videoBean = gson.fromJson(object.toString(), VideoBean.class);
                    list.add(videoBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static String getWebviewJson(String json) {
        Gson gson = new Gson();
        NewBean newBean = gson.fromJson(json, NewBean.class);
        String url = newBean.data.url;
        return url;
    }
}
