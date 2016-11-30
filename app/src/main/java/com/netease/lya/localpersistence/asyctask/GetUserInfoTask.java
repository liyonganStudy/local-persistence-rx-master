package com.netease.lya.localpersistence.asyctask;

import android.content.Context;
import android.os.AsyncTask;

import com.netease.lya.localpersistence.data.UserInfo;
import com.netease.lya.localpersistence.data.source.local.UserInfoLocalDataSource;

import java.util.List;

/**
 * Created by netease on 16/11/30.
 * get all UserInfo
 */
public abstract class GetUserInfoTask extends AsyncTask<Void, Void, List<UserInfo>> {

    private Context context;

    public GetUserInfoTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<UserInfo> doInBackground(Void... params) {
        return UserInfoLocalDataSource.getInstance(context).getAllUserInfo();
    }

    @Override
    protected void onPostExecute(List<UserInfo> userInfo) {
        super.onPostExecute(userInfo);
        onGetAllUserInfo(userInfo);
    }

    protected abstract void onGetAllUserInfo(List<UserInfo> allUserInfo);
}
