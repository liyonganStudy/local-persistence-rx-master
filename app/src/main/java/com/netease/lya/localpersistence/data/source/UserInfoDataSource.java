package com.netease.lya.localpersistence.data.source;

import com.netease.lya.localpersistence.data.UserInfo;

import java.util.List;

/**
 * Created by netease on 16/11/30.
 * UserInfo DataSource
 */
public interface UserInfoDataSource {

    void addUserInfo(UserInfo userInfo);

    void removeUserInfo(UserInfo userInfo);

    void updateUserInfo(UserInfo userInfo);

    List<UserInfo> getAllUserInfo();
}
