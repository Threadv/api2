package com.ifenghui.storybookapi.app.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wml on 2016/12/26.
 * 搜索关键词推荐
 */
@Entity
@Table(name="story_hotword")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "搜索关键词推荐",addAble = true,editAble = true,deleteAble = true)
public class Hotword {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id")
    Long id;
//    @CmsColumn(name="词语")
    String content;
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;
//    @CmsColumn(name="创建时间",isShowToAdd = false,isShowToEdit = false,defaultValueType = CmsDefaultValueType.DATE_NOW)
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
