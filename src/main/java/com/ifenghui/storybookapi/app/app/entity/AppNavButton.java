package com.ifenghui.storybookapi.app.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;

/**
 * app底部栏的图标表
 */
@Entity
@Table(name="story_app_nav_button")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppNavButton {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @JsonIgnore
    String proSelectIcon;

    @JsonProperty("selectPreIcon")
    String getProSelectUrl(){
        return MyEnv.env.getProperty("oss.url")+"appNav/" + proSelectIcon;
    }

    @JsonIgnore
    String defaultSelectIcon;

    @JsonProperty("selectIcon")
    String getDefaultSelectUrl(){
        return MyEnv.env.getProperty("oss.url")+"appNav/" + defaultSelectIcon;
    }

    @JsonIgnore
    String defaultUnselectIcon;

    @JsonProperty("defaultIcon")
    String getDefaultUnSelectUrl(){
        return MyEnv.env.getProperty("oss.url")+"appNav/" + defaultUnselectIcon;
    }

    String title;

    @JsonIgnore
    String bgIcon;

    @JsonProperty("bgIcon")
    public String getBgIconUrl(){
        return MyEnv.env.getProperty("oss.url")+"appNav/" + bgIcon;
    }

    Integer deviceType;

    Integer status;

    Integer orderBy;

    String selectColor;

    String unSelectColor;

    Integer style;

    @JsonProperty("defaultColor")
    public String getDefaultUnSelectColor(){
        return this.unSelectColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProSelectIcon() {
        return proSelectIcon;
    }

    public void setProSelectIcon(String proSelectIcon) {
        this.proSelectIcon = proSelectIcon;
    }

    public String getDefaultSelectIcon() {
        return defaultSelectIcon;
    }

    public void setDefaultSelectIcon(String defaultSelectIcon) {
        this.defaultSelectIcon = defaultSelectIcon;
    }

    public String getDefaultUnselectIcon() {
        return defaultUnselectIcon;
    }

    public void setDefaultUnselectIcon(String defaultUnselectIcon) {
        this.defaultUnselectIcon = defaultUnselectIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgIcon() {
        return bgIcon;
    }

    public void setBgIcon(String bgIcon) {
        this.bgIcon = bgIcon;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public String getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(String selectColor) {
        this.selectColor = selectColor;
    }

    public String getUnSelectColor() {
        return unSelectColor;
    }

    public void setUnSelectColor(String unSelectColor) {
        this.unSelectColor = unSelectColor;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }
}
