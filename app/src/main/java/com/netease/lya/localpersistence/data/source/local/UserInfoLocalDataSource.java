package com.netease.lya.localpersistence.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.netease.lya.localpersistence.data.UserInfo;
import com.netease.lya.localpersistence.data.source.UserInfoDataSource;
import com.netease.lya.localpersistence.data.source.local.UserInfoPersistenceContract.UserInfoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netease on 16/11/30.
 * data source local implement
 */
public class UserInfoLocalDataSource implements UserInfoDataSource {

    private static UserInfoLocalDataSource INSTANCE;

    private UserInfoDbHelper dbHelper;

    private UserInfoLocalDataSource(Context context) {
        dbHelper = new UserInfoDbHelper(context);
    }

    public static UserInfoLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserInfoEntry.COLUMN_NAME_UID, userInfo.getUid());
        contentValues.put(UserInfoEntry.COLUMN_NAME_USER_NAME, userInfo.getName());
        contentValues.put(UserInfoEntry.COLUMN_NAME_PASSWORD, userInfo.getPassword());

        db.insert(UserInfoEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    @Override
    public void removeUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = UserInfoEntry.COLUMN_NAME_UID + " LIKE ?";
        String[] selectionArgs = {userInfo.getUid()};

        db.delete(UserInfoEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = UserInfoEntry.COLUMN_NAME_UID + " LIKE ?";
        String[] selectionArgs = {userInfo.getUid()};

        ContentValues values = new ContentValues();
        values.put(UserInfoEntry.COLUMN_NAME_USER_NAME, userInfo.getName());
        values.put(UserInfoEntry.COLUMN_NAME_PASSWORD, userInfo.getPassword());

        db.update(UserInfoEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                UserInfoEntry.COLUMN_NAME_PASSWORD,
                UserInfoEntry.COLUMN_NAME_UID,
                UserInfoEntry.COLUMN_NAME_USER_NAME
        };
        Cursor cursor = db.query(UserInfoEntry.TABLE_NAME, columns, null, null, null, null, null);

        List<UserInfo> allUserInfo = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String uid = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_UID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_USER_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoEntry.COLUMN_NAME_PASSWORD));
                UserInfo userInfo = new UserInfo(uid, name, password);
                allUserInfo.add(userInfo);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return allUserInfo;
    }
}
