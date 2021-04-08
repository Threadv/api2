package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.AdPositionStyle;
import com.ifenghui.storybookapi.util.VersionUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 *
 */
@Entity
@Table(name="story_serial_banner")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class SerialBanner implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

//    @CmsColumn(name="标题")
    String title;

    String banner;

    Integer serialId;
//    @CmsColumn(name="外链")
//    String url;

//    @CmsColumn(name="发布状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

//   正序
    Integer position;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    @JsonIgnore
    Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public String getBannerUrl() {

        return MyEnv.env.getProperty("oss.url")+banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}
