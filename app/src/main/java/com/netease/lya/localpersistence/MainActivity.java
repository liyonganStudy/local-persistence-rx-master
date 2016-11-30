package com.netease.lya.localpersistence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.lya.localpersistence.asyctask.DeleteUserInfoTask;
import com.netease.lya.localpersistence.asyctask.GetUserInfoTask;
import com.netease.lya.localpersistence.data.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView userInfoList = (ListView) findViewById(R.id.userInfoList);
        adapter = new UserInfoAdapter();
        userInfoList.setAdapter(adapter);
        userInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditUserInfoActivity.launch(MainActivity.this, (UserInfo) adapter.getItem(position));
            }
        });
        findViewById(R.id.addUserInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserInfoActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUserInfoTask task = new GetUserInfoTask(MainActivity.this) {
                    @Override
                    protected void onGetAllUserInfo(List<UserInfo> allUserInfo) {
                        adapter.setData(allUserInfo);
                    }
                };
                task.execute();
            }
        });
    }

    private static class UserInfoAdapter extends BaseAdapter {

        private List<UserInfo> data = new ArrayList<>();

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<UserInfo> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.password = (TextView) convertView.findViewById(R.id.password);
                viewHolder.remove = (TextView) convertView.findViewById(R.id.removeUserInfo);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final UserInfo userInfo = (UserInfo) getItem(position);
            viewHolder.name.setText(userInfo.getName());
            viewHolder.password.setText(userInfo.getPassword());
            viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteUserInfoTask task = new DeleteUserInfoTask(parent.getContext(), userInfo);
                    task.execute();
                }
            });
            return convertView;
        }

        static class ViewHolder {
            TextView name;
            TextView password;
            TextView remove;
        }
    }
}
