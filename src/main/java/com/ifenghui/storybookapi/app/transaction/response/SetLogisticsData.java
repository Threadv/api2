package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by wml on 2017/11/10.
 */
public class SetLogisticsData {

    String status;

    String msg;

    Logistics result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Logistics getResult() {
        return result;
    }

    public void setResult(Logistics result) {
        this.result = result;
    }
}
