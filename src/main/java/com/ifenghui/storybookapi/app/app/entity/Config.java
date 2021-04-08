package com.ifenghui.storybookapi.app.app.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 通用配置表
 */
@Entity
@Table(name="story_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="配置管理",addAble = false)
/**
 * Created by wml on 2017/5/31
 */
public class Config implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column
//    @CmsColumn(name="介绍")
    String intro;
    @Column(name="`key`")
    String key;
    @Column
//    @CmsColumn(name="val")
    String val;
    @Column
    @JsonIgnore
//    @CmsColumn(name="创建时间")
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTimeFormat(){
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
