package com.ifenghui.storybookapi.app.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wml on 2016/12/23.
 * 热销故事的记录表
 */
@Entity
@Table(name="story_story_hot_sale")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Deprecated
//@CmsTable(name="热销故事",addAble = false,editAble = false,deleteAble = false)
public class StoryHotSale {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    //    Long storyId;
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事",inputType= CmsInputType.FORGIN,foreignName = "故事购买记录")
    Story story;

    Integer storyId;

//    @CmsColumn(name="创建时间")
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
