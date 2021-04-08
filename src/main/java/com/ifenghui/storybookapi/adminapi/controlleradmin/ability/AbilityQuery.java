package com.ifenghui.storybookapi.adminapi.controlleradmin.ability;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AbilityQuery {
    Integer id;
    Integer userId;
    Integer isDel;
    Integer status;
    String code;
//      Integer pageNo;
//    Integer pageSize;
     Integer isTest;
    Integer isCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date beginSuccessTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endSuccessTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date beginCreateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endCreateTime;

    Integer isBaobao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Integer getIsCode() {
        return isCode;
    }

    public void setIsCode(Integer isCode) {
        this.isCode = isCode;
    }

    public Date getBeginSuccessTime() {
        return beginSuccessTime;
    }

    public void setBeginSuccessTime(Date beginSuccessTime) {
        this.beginSuccessTime = beginSuccessTime;
    }

    public Date getEndSuccessTime() {
        return endSuccessTime;
    }

    public void setEndSuccessTime(Date endSuccessTime) {
        this.endSuccessTime = endSuccessTime;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Integer getIsBaobao() {
        return isBaobao;
    }

    public void setIsBaobao(Integer isBaobao) {
        this.isBaobao = isBaobao;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
