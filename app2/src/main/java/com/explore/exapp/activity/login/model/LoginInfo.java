package com.explore.exapp.activity.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryan on 14/11/9.
 */
public class LoginInfo {

    @SerializedName("L1")
    private String l1;
    @SerializedName("L2")
    private String l2;
    @SerializedName("L3")
    private String l3;
    @SerializedName("USERNAME")
    private String userName;
    @SerializedName("REALNAME")
    private String realName;
    @SerializedName("AUDITCOUNT")
    private String auditCount;
    @SerializedName("TOKEN")
    private String token;
    @SerializedName("USERID")
    private String userId;

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public String getL3() {
        return l3;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(String auditCount) {
        this.auditCount = auditCount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
