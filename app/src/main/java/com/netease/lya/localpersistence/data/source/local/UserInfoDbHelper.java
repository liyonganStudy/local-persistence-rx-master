package com.netease.lya.localpersistence.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.netease.lya.localpersistence.data.source.local.UserInfoPersistenceContract.UserInfoEntry;

/**
 * Created by netease on 16/11/30.
 *
 */
public class UserInfoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "UserInfo.db";

    private static final String TEXT_TYPE = " TEXT";

//    private static final String BOOLEAN_TYPE = " INTEGER";

//    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER_INFO_TABLE =
            "CREATE TABLE " + UserInfoEntry.TABLE_NAME + " (" +
                    UserInfoEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    UserInfoEntry.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    UserInfoEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    UserInfoEntry.COLUMN_NAME_UID + TEXT_TYPE + " )";

    public UserInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
