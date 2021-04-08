package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 故事的ip标签
 */
@Entity
@Table(name="story_ip_label")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IpLabel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer parentId;
    Integer ipId;
    String content;
    Integer status;
    Integer orderBy;
    Date createTime;
    String color;

    Integer ipBrandId;

    //二级标签
    @Transient
    List<IpLabel> ipLabelList;


    //wsl 2.12版本增加
    @Transient
    List<IpLabelStory> ipLabelStories;

    public IpLabel(){}

    public IpLabel(Integer parentId, Integer ipId, String content, String color) {
        this.id = 0;
        this.parentId = parentId;
        this.ipId = ipId;
        this.content = content;
        this.color = color;
        this.createTime = new Date();
        this.status = 1;
        this.orderBy = 0;
    }

    public Integer getIpBrandId() {
        return ipBrandId;
    }

    public void setIpBrandId(Integer ipBrandId) {
        this.ipBrandId = ipBrandId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIpId() {
        return ipId;
    }

    public void setIpId(Integer ipId) {
        this.ipId = ipId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<IpLabel> getIpLabelList() {
        return ipLabelList;
    }

    public void setIpLabelList(List<IpLabel> ipLabelList) {
        this.ipLabelList = ipLabelList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<IpLabelStory> getIpLabelStories() {
        return ipLabelStories;
    }

    public void setIpLabelStories(List<IpLabelStory> ipLabelStories) {
        this.ipLabelStories = ipLabelStories;
    }
}
