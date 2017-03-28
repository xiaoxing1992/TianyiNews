package tianyinews.tianyi.com.tianyinews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/27.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USERS = "create table users(_id integer primary key autoincrement,uid text,name text,imgurl text)";
    public static final String CREATE_PHONEUSERS = "create table phoneusers(_id integer primary key autoincrement,phonenumber text,phonename text,imgurl text)";

    public MyDataBaseHelper(Context context) {
        super(context, "myuser.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_PHONEUSERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
