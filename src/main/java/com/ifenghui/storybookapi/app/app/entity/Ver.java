package com.ifenghui.storybookapi.app.app.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="story_ver")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="版本管理",addAble = false)
/**
 * Created by wml on 2017/4/10
 * 版本表
 */
public class Ver{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(updatable = false)
//    @CmsColumn(name="版本号")
    String ver;

    @Column
//    @CmsColumn(name="大小")
    String size;

    @Column
//    @CmsColumn(name="描述")
    String intro;

    @Transient
//    @CmsColumn(name = "渠道名称",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    String url;

    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

    @JsonIgnore
    Integer appId;


    //是否需要强更
    @Transient
    Integer isUpdate;

    //是否需要更新
    @Transient
    Integer isUper;

    @JsonIgnore
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

    @Transient
//    @CmsColumn(name = "渠道名称",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    String channelName;

    @JsonIgnore
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getIsUper() {
        return isUper;
    }

    public void setIsUper(Integer isUper) {
        this.isUper = isUper;
    }
}
