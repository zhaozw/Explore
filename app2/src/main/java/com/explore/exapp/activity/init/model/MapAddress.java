package com.explore.exapp.activity.init.model;

import org.litepal.crud.DataSupport;

/**
 * Created by ryan on 14/11/19.
 */
public class MapAddress extends DataSupport {

    private int id;

    private String type;

    private String name;

    private String value;

    private int addressOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAddressOrder() {
        return addressOrder;
    }

    public void setAddressOrder(int addressOrder) {
        this.addressOrder = addressOrder;
    }
}
