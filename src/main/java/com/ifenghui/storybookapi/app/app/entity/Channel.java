package com.ifenghui.storybookapi.app.app.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 故事飞船渠道表
 */
@Entity
@Table(name="story_channel")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="版本管理",addAble = false)
/**
 * Created by wml on 2017/4/10
 */
public class Channel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(updatable = false)
//    @CmsColumn(name="名称")
    String name;


    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

    @JsonIgnore
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

    @JsonIgnore
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

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
