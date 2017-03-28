package tianyinews.tianyi.com.tianyinews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tianyinews.tianyi.com.tianyinews.bean.PhoneUserBean;
import tianyinews.tianyi.com.tianyinews.bean.UserBean;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */

public class UserDao {

    private MyDataBaseHelper dataBaseHelper;

    public UserDao(Context context) {
        dataBaseHelper = new MyDataBaseHelper(context);
    }

    //向QQ用户表插入用户数据
    public boolean insertUserByQQ(String uid, String name, String imgUrl) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", uid);
        values.put("name", name);
        values.put("imgurl", imgUrl);
        long l = db.insert("users", null, values);
        if (l > 0) {
            return true;
        } else {
            return false;
        }
    }

    //查询qq用户表中的数据
    public ArrayList<UserBean> queryUserByQQ() {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        ArrayList<UserBean> userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserBean userBean = new UserBean();
            userBean.uid = cursor.getString(cursor.getColumnIndex("uid"));
            userBean.name = cursor.getString(cursor.getColumnIndex("name"));
            userBean.imgUrl = cursor.getString(cursor.getColumnIndex("imgurl"));
            userList.add(userBean);
        }
        return userList;
    }

    //向phone用户表插入用户数据
    public boolean insertUserByPhone(int phoneNumber, String phoneName, String imgurl) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phonenumber", phoneNumber);
        values.put("phonename", phoneName);
        values.put("imgurl", imgurl);
        long l = db.insert("phoneusers", null, values);
        if (l > 0) {
            return true;
        } else {
            return false;
        }
    }

    //查询phone用户表中的数据
    public ArrayList<PhoneUserBean> queryUserByPhone() {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query("phoneusers", null, null, null, null, null, null);
        ArrayList<PhoneUserBean> userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            PhoneUserBean phoneUserBean = new PhoneUserBean();
            phoneUserBean.phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
            phoneUserBean.phonename = cursor.getString(cursor.getColumnIndex("phonename"));
            phoneUserBean.imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));
            userList.add(phoneUserBean);
        }
        return userList;
    }
}
