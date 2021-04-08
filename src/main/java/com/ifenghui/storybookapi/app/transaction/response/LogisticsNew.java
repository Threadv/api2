package com.ifenghui.storybookapi.app.transaction.response;

import java.util.List;

/**
 * Created by wml on 2017/11/10.
 */
public class LogisticsNew {
    String number;

    String type;

    String address;

    List<LogisticsInfo> list;

    String issign;

    String deliverystatus;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LogisticsInfo> getList() {
        return list;
    }

    public void setList(List<LogisticsInfo> list) {
        this.list = list;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }
}
