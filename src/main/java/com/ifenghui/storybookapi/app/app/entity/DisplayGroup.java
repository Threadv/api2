package com.ifenghui.storybookapi.app.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.app.story.entity.Story;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */

/**
 * 首页分组表
 */
@Entity
@Table(name="story_group")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "首页分组",intro = "")
public class DisplayGroup implements Serializable {
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    @CmsColumn(name="名称",intro = "分组名",isShowToInner = true)
    String name;

    String content;


//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "groupIcon/")
//    @CmsColumn(name="图标",inputType = CmsInputType.FILE,fileType = CmsFileType.IMAGE)
    @JsonIgnore
    String icon;



    @Transient
    String padIcon;
//    @CmsColumn(name="跳转类型",enumClassName = GroupTargetType.class,inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP)
    Integer targetType;

    @Transient
//    @CmsColumn(name="目标id",intro = "分组id/故事集id",defaultValueType =CmsDefaultValueType.CUSTOM,defaultValue = "0",isShowToAdd = false)
    Integer targetValue;


//    @CmsColumn(name="发布状态",enumClassName = PublishStatus.class,inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP)
    Integer status;

    //后台是否显示为常用功能
    Integer isShow;
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;
    @Transient
    Integer isSubscribe;
    @Override
    public String toString(){
        return name;
    }
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false)
    @Transient
//    @ManyToMany(cascade={CascadeType.REFRESH},targetEntity = Story.class,fetch= FetchType.LAZY)
//    @JoinTable(name="story_group_relevance",    joinColumns={@JoinColumn(name="groupId",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="storyId",referencedColumnName="id")})
    List<Story> storys;

//    @JsonIgnore
//    @Transient
//    List<GroupRelevance> groupRelevance;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonIgnore
    public String getIcon() {
        return icon;
    }
    @JsonProperty("icon")
    String getIconUrl(){
        return MyEnv.env.getProperty("oss.url")+"groupIcon/"+icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPadIcon() {
        this.padIcon= MyEnv.env.getProperty("oss.url")+"groupIcon/"+ this.padIcon;
        return padIcon;
    }

    public void setPadIcon(String padIcon) {
        this.padIcon = padIcon;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetValue() {
        if(targetValue ==null){
            targetValue = 0;
        }
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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
    public Integer getIsSubscribe() {
        if(isSubscribe == null){
            isSubscribe = 0;
        }
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }
    public List<Story> getStorys() {
        return storys;
    }

    public void setStorys(List<Story> storys) {
        if(storys!=null){
            for(Story story:storys){
                story.setRefererGroupId(this.getId().intValue());
            }
        }
        this.storys = storys;
    }


}
