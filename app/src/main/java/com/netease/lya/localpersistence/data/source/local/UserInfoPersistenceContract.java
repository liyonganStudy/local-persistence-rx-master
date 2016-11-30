package com.netease.lya.localpersistence.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by netease on 16/11/30.
 * 定义数据库相关的一些常量
 */
public class UserInfoPersistenceContract {

    private UserInfoPersistenceContract() {}

    public static abstract class UserInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_info";
        public static final String COLUMN_NAME_USER_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_UID = "uid";
    }

}
