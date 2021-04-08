package com.ifenghui.storybookapi.app.story.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 故事标签（最早的标签）
 */
@Entity
@Table(name="story_label")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="故事标签")
/**
 * Created by jia on 2016/12/22.
 */
public class Label implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    @CmsColumn(name="标题")
    String content;
    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;
    @JsonIgnore
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

    @JsonIgnore
//    @CmsColumn(name="创建时间",defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;
    @Override
    public String toString(){
        return this.content;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
