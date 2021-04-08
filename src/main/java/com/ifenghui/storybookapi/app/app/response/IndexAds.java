package com.ifenghui.storybookapi.app.app.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

public class IndexAds {

    Long id;

    String title;

    String content;

    String imgPath;

    String icon;

    String banner;

    Integer targetType;

    Integer targetValue;

    String url;

    Integer width;

    Integer height;

    public IndexAds(Ads ads){
        this.id = ads.getId();
        this.imgPath = ads.getImgPath();
        this.content = ads.getContent();
        this.title = ads.getTitle();
        this.icon = ads.getIcon();
        this.banner = ads.getBanner();
        this.targetType = ads.getTargetType();
        this.targetValue = ads.getTargetValue();
        this.url = ads.getUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getWidth() {
        this.width = 750;
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        this.height = 1080;
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
