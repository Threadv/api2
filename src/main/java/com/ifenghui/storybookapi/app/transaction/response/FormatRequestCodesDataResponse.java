package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wml on 2016/12/30.
 */
public class FormatRequestCodesDataResponse  {
    Long id;
    String code;
    Integer month;

    Long user_id;
    Integer is_activate;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @JsonIgnore
    Integer status;
    String create_time;
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getIs_activate() {
        return is_activate;
    }

    public void setIs_activate(Integer is_activate) {
        this.is_activate = is_activate;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Date getCreateTime() {

        if(this.create_time==null){
            return  null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           return sdf.parse(this.create_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
