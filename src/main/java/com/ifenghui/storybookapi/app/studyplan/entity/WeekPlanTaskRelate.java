package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.LabelTargetStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 周计划小任务关联和描述
 */
@Entity
@Table(name = "story_week_plan_task_relate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanTaskRelate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer weekPlanId;
    Integer dayNum;
    Integer storyId;
    Integer storyType;
    Integer orderBy;
    String name;
    Date createTime;

    Integer targetType;

    Integer score;

    @Transient
    Integer isFinish;

    /**
     * 家长导读页面地址
     */
    @Transient
    String parentsGuideUrl;

    /**
     * 家长导读内容
     */
    @JsonIgnore
    String content;

    /**
     * 家长导读类型 0图文 1图片
     */
    @JsonIgnore
    Integer type;


    /**图标链接*/
    @JsonProperty("url")
    public String getUrl() {

        int str = 1000001;
        return "http://storybook.oss-cn-hangzhou.aliyuncs.com/weekplan/week_img_v2_"+storyType+".png?t="+str;
    }

    @JsonProperty("isSingle")
    public Integer getIsSingle() {

        if (this.storyType == 2 || this.storyType == 7) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParentsGuideUrl() {
        return parentsGuideUrl;
    }

    public void setParentsGuideUrl(String parentsGuideUrl) {
        this.parentsGuideUrl = parentsGuideUrl;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekPlanId() {
        return weekPlanId;
    }

    public void setWeekPlanId(Integer weekPlanId) {
        this.weekPlanId = weekPlanId;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public Integer getStoryType() {
        return storyType;
    }

    public void setStoryType(Integer storyType) {
        this.storyType = storyType;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LabelTargetStyle getTargetType() {
        return LabelTargetStyle.getById(targetType);
    }

    public void setTargetType(LabelTargetStyle targetType) {
        this.targetType = targetType.getId();
    }
}
