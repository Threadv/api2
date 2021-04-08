package com.ifenghui.storybookapi.api.response.base;

/**
 * Created by wslhk on 2016/12/20.
 */
public class ApiStatus {
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
