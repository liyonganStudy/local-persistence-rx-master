package com.netease.lya.localpersistence.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.netease.lya.localpersistence.data.UserInfo;
import com.netease.lya.localpersistence.data.source.UserInfoDataSource;
import com.netease.lya.localpersistence.data.source.local.UserInfoPersistenceContract.UserInfoEntry;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by netease on 16/11/30.
 * data source local implement
 */
public class UserInfoLocalDataSource implements UserInfoDataSource {

    private static UserInfoLocalDataSource INSTANCE;

    private BriteDatabase database;
    private Func1<Cursor, UserInfo> userInfoMapper;

    private UserInfoLocalDataSource(Context context) {
        UserInfoDbHelper dbHelper = new UserInfoDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        database = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        userInfoMapper = new Func1<Cursor, UserInfo>() {
            @Override
            public UserInfo call(Cursor cursor) {
                String uid = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_UID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_USER_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_PASSWORD));
                return new UserInfo(uid, name, password);
            }
        };
    }

    public static UserInfoLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void addUserInfo(UserInfo userInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserInfoEntry.COLUMN_NAME_UID, userInfo.getUid());
        contentValues.put(UserInfoEntry.COLUMN_NAME_USER_NAME, userInfo.getName());
        contentValues.put(UserInfoEntry.COLUMN_NAME_PASSWORD, userInfo.getPassword());

        database.insert(UserInfoEntry.TABLE_NAME, contentValues);
    }

    @Override
    public void removeUserInfo(UserInfo userInfo) {
        String selection = UserInfoEntry.COLUMN_NAME_UID + " LIKE ?";
        String[] selectionArgs = {userInfo.getUid()};

        database.delete(UserInfoEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        String selection = UserInfoEntry.COLUMN_NAME_UID + " LIKE ?";
        String[] selectionArgs = {userInfo.getUid()};

        ContentValues values = new ContentValues();
        values.put(UserInfoEntry.COLUMN_NAME_USER_NAME, userInfo.getName());
        values.put(UserInfoEntry.COLUMN_NAME_PASSWORD, userInfo.getPassword());

        database.update(UserInfoEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public Observable<List<UserInfo>> getAllUserInfo() {

        String[] columns = {
                UserInfoEntry.COLUMN_NAME_PASSWORD,
                UserInfoEntry.COLUMN_NAME_UID,
                UserInfoEntry.COLUMN_NAME_USER_NAME
        };

        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", columns), UserInfoEntry.TABLE_NAME);
        return database.createQuery(UserInfoEntry.TABLE_NAME, sql).mapToList(userInfoMapper);
    }
}
