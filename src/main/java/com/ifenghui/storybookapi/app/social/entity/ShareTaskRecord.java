package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 分享邀请任务记录
 */
@Entity
@Table(name="story_share_task_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="分享邀请任务记录",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by jia on 2017/11/09.
 */
public class ShareTaskRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    Long userId;
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
