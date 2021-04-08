package com.ifenghui.storybookapi.app.app.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.RedirectStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统消息表
 */
@Entity
@Table(name = "story_notice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //内容
    String content;

    @Transient
    String name;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "MM-dd")
    Date createTime;

    //消息类型
    Integer targetType;

    Integer targetValue;

    //跳转链接
    String url;

    //图片
    @JsonIgnore
    String icon;

    @JsonProperty("icon")
    String getIconUrl(){
        if(!icon.equals("")){
            return MyEnv.env.getProperty("oss.url") + "notice/" + icon;
        } else {
            return icon;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTargetType() {
        return targetType;
    }

//    public void setTargetType(Integer targetType) {
//        this.targetType = targetType;
//    }

    public void setRedirectStyle(RedirectStyle redirectStyle) {
        this.targetType = redirectStyle.getId();
    }

    public RedirectStyle getRedirectStyle(){
        if(targetType!=null){
            return  RedirectStyle.getById(targetType);
        }
        return null;
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

    public String getName() {
        return "故事飞船";
    }

    public void setName(String name) {
        this.name = name;
    }
}
