package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="story_paster")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Deprecated
//碎片不再更新
public class Paster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    @JsonIgnore
    Integer orderBy;

    @JsonIgnore
    String icon;

    @JsonProperty("icon")
    String getIconUrl(){
        return MyEnv.env.getProperty("oss.url")+"paster/"+icon;
    }

    @JsonIgnore
    String filePath;

    @JsonProperty("filePath")
    String getFilePathUrl(){
        return MyEnv.env.getProperty("oss.url")+"paster/"+filePath;
    }

    @JsonIgnore
    Integer status;

    @JsonIgnore
    Date createTime;

    @JsonIgnore
    Integer type;

    Integer model;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }
}
