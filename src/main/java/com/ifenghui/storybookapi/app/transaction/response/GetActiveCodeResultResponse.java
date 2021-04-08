package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetActiveCodeResultResponse {

    Integer code;

    String status;

    Integer month;

    String is_activate;

    Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getIs_activate() {
        return is_activate;
    }

    public void setIs_activate(String is_activate) {
        this.is_activate = is_activate;
    }
}
