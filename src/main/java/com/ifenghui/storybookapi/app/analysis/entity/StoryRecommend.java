package com.ifenghui.storybookapi.app.analysis.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="story_story_recommend")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="推荐故事管理",addAble = false)
/**
 * Created by wml on 2017/4/17
 * 推荐故事
 */
public class StoryRecommend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @JsonIgnore
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

    @JsonIgnore
//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

    @JsonIgnore
    @JoinColumn(name = "relStoryId",insertable = true, updatable = true)
//    @CmsColumn(name="relStoryId",defaultValue = "0")
    Long relStoryId;

    Integer storyId;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事",inputType= CmsInputType.FORGIN,foreignName = "推荐故事")
            Story story;

    @JsonIgnore
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
        if(story != null) {
            this.storyId = story.getId().intValue();
        }
    }

    public Long getRelStoryId() {
        return relStoryId;
    }

    public void setRelStoryId(Long relStoryId) {
        this.relStoryId = relStoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
