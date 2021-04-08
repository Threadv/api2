package com.ifenghui.storybookapi.app.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */

/**
 * 首页分组对象
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

//@CmsTable(name = "首页分组",intro = "")
public class CustomGroup implements Serializable{

    Long id;

//    @CmsColumn(name="名称",intro = "分组名",isShowToInner = true)
    String name;

    String content;

    String icon;

    String padIcon;

    Integer targetType;//

    Integer targetValue;

    Integer status;

//    Date createTime;

    Integer isSubscribe;

    Integer isAbilityPlan;

    int showType=1;
    @Override
    public String toString(){
        return name;
    }

    List<Story> storys;
    List<SerialStory> serialStories;
    //故事足迹
    List<ViewRecord> viewRecordList;
    Ads ads;

    //新ip专区
    List<IpBrand> ipBrands;


    public CustomGroup(){

    }
    public CustomGroup(DisplayGroup displayGroup){

        this.id=displayGroup.getId();
        this.name=displayGroup.getName();
        this.content=displayGroup.getContent();
        this.icon=displayGroup.getIcon();
        this.targetType=displayGroup.getTargetType();
        this.targetValue=displayGroup.getTargetValue();
        showType=1;
    }

    public CustomGroup(List<SerialStory> serialStorys){
        this.serialStories=serialStorys;
        showType=2;

    }


    public Long getId() {
        if(id ==null){
            id = 0L;
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        if(name ==null){
            name = "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        if(content ==null){
            content = "";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        if(icon ==null){
            icon = "";
        }
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPadIcon() {
        if(padIcon ==null){
            padIcon = "";
        }
        return padIcon;
    }

    public void setPadIcon(String padIcon) {
        this.padIcon = padIcon;
    }

    public Integer getIsAbilityPlan() {
        return isAbilityPlan;
    }

    public void setIsAbilityPlan(Integer isAbilityPlan) {
        this.isAbilityPlan = isAbilityPlan;
    }

    public Integer getTargetType() {
        if(targetType ==null){
            targetType = 0;
        }
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
        if(status ==null){
            status = 0;
        }
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

    public Integer getIsSubscribe() {
        if(isSubscribe ==null){
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
        this.storys = storys;
    }

    public List<SerialStory> getSerialStories() {
        return serialStories;
    }

    public void setSerialStories(List<SerialStory> serialStories) {
        this.serialStories = serialStories;
    }

    public List<ViewRecord> getViewRecordList() {
        return viewRecordList;
    }

    public void setViewRecordList(List<ViewRecord> viewRecordList) {
        this.viewRecordList = viewRecordList;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public List<IpBrand> getIpBrands() {
        return ipBrands;
    }

    public void setIpBrands(List<IpBrand> ipBrands) {
        this.ipBrands = ipBrands;
    }
}
