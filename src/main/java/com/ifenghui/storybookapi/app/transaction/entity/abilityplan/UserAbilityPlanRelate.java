package com.ifenghui.storybookapi.app.transaction.entity.abilityplan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.AbilityPlanStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_user_ability_plan_relate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAbilityPlanRelate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer userId;
    Integer type;
    Date startTime;
    Date endTime;
    Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setAbilityPlanStyle(AbilityPlanStyle abilityPlanStyle){
        this.type = abilityPlanStyle.getId();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
