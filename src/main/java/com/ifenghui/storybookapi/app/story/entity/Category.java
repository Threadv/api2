package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/15.
 */

/**
 * 故事栏目表
 */
@Entity
@Table(name="story_category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "故事分类",intro = "")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    @CmsColumn(name="名称",intro = "分组名")
    String name;


//    @CmsColumn(name="发布状态")
    Integer status;

//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW)
    Date createTime;
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false)
    @Transient
    List<Story> storys;
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

    public List<Story> getStorys() {
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }
}
