package com.example.abl.myapplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.abl.myapplication.Model.ItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABL on 30.01.2018.
 */

public class SqliteN extends SQLiteOpenHelper {

    private static final String TAG = SqliteN.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sttnf_result";

    private static final String TABLE_NOTE = "table_note";

    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_PICTURE = "picture";
    public static final String KEY_LOCATION = "location";

    public SqliteN(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_TIME + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_PICTURE + " TEXT,"
                + KEY_LOCATION + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE  " + TABLE_NOTE);
        onCreate(sqLiteDatabase);
    }

    public void addData(String time, String title, String content, String picture, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!CheckData(title)) {
            ContentValues values = new ContentValues();
            values.put(KEY_TIME, time);
            values.put(KEY_TITLE, title);
            values.put(KEY_CONTENT, content);
            values.put(KEY_PICTURE, picture);
            values.put(KEY_LOCATION, location);

            db.insert(TABLE_NOTE, null, values);
        } else {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE " + TABLE_NOTE +
                        " SET time='" + time + "', title='" + title + "', content='" + content +
                        "', picture='" + picture + "', location='" + location + "' WHERE title='" + title + "'");
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        db.close();
    }

    public List<ItemObject> getData() {
        List<ItemObject> usersdetail = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_NOTE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ItemObject itemObject = new ItemObject();
                    itemObject.time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
                    itemObject.title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                    itemObject.content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT));
                    itemObject.picture = cursor.getString(cursor.getColumnIndex(KEY_PICTURE));
                    itemObject.location = cursor.getString(cursor.getColumnIndex(KEY_LOCATION));
                    usersdetail.add(itemObject);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return usersdetail;
    }

    public boolean CheckData(String title) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_NOTE + " WHERE title='" + title + "'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteItemSelected(String time) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_NOTE + " WHERE time ='" + time + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error");
        } finally {
            db.endTransaction();
        }
    }

}