package com.netease.lya.localpersistence.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by netease on 16/11/30.
 * UserInfo
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7786240088366908103L;
    private String uid;
    private String name;
    private String password;

    public UserInfo() {
        this("", "");
    }

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
        this.uid = UUID.randomUUID().toString();
    }

    public UserInfo(String uid, String name, String password) {
        this.uid = uid;
        this.name = name;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
