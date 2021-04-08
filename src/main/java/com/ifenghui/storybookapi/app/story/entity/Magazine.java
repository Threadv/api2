package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/15.
 */

/**
 * 期刊表 （最早使用）
 * 已经不维护
 */
@Entity
@Table(name="story_magazine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//订阅排期
@Deprecated
public class Magazine {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    @Transient
//    @CmsColumn(name="名称",intro = "期刊名")
    String name;

    Integer isNow;

    Integer status;

    Integer orderBy;

    Date publishTime;

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    @Transient
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    List<Story> storys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsNow() {
        return isNow;
    }

    public void setIsNow(Integer isNow) {
        this.isNow = isNow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Story> getStorys() {
        if(storys==null){storys=new ArrayList();}
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }
    @Override
    public String toString(){
        return this.name==null?"":this.name;
    }
}
