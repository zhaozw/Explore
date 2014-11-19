package com.explore.exapp.activity.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryan on 14/11/19.
 */
public class CustomerDept {

    @SerializedName("CUSTOMERDEPTID")
    private String id;
    @SerializedName("CUSTOMERDEPTNAME")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
