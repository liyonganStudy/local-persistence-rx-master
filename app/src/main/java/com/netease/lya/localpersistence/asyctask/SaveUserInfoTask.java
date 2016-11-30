package com.netease.lya.localpersistence.asyctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.netease.lya.localpersistence.data.UserInfo;
import com.netease.lya.localpersistence.data.source.local.UserInfoLocalDataSource;

/**
 * Created by netease on 16/11/30.
 * 新增UserInfo
 */
public class SaveUserInfoTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private UserInfo userInfo;

    public SaveUserInfoTask(Context context, UserInfo userInfo) {
        this.context = context;
        this.userInfo = userInfo;
    }

    @Override
    protected Void doInBackground(Void... params) {
        UserInfoLocalDataSource.getInstance(context).addUserInfo(userInfo);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "save success", Toast.LENGTH_SHORT).show();
    }

}
