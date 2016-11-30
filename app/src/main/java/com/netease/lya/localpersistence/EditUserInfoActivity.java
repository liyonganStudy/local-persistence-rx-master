package com.netease.lya.localpersistence;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.netease.lya.localpersistence.asyctask.SaveUserInfoTask;
import com.netease.lya.localpersistence.asyctask.UpdateUserInfoTask;
import com.netease.lya.localpersistence.data.UserInfo;

public class EditUserInfoActivity extends AppCompatActivity {

    public static final String INTENT_KEY_USER_INFO = "user_info";

    private EditText name;
    private EditText password;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra(INTENT_KEY_USER_INFO);

        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.pass);
        Button addButton = (Button) findViewById(R.id.save);

        if (userInfo != null) {
            name.setText(userInfo.getName());
            password.setText(userInfo.getPassword());
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedName = name.getText().toString();
                String editedPass = password.getText().toString();
                if (userInfo != null) {
                    userInfo.setName(editedName);
                    userInfo.setPassword(editedPass);
                    UpdateUserInfoTask task = new UpdateUserInfoTask(EditUserInfoActivity.this, userInfo);
                    task.execute();
                } else {
                    userInfo = new UserInfo(editedName, editedPass);
                    SaveUserInfoTask task = new SaveUserInfoTask(EditUserInfoActivity.this, userInfo);
                    task.execute();
                }

            }
        });
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        context.startActivity(intent);
    }

    public static void launch(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.putExtra(INTENT_KEY_USER_INFO, userInfo);
        context.startActivity(intent);
    }
}
