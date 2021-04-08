package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.style.VipFriendCardStatusStyle;
import com.ifenghui.storybookapi.style.VipFriendCardTypeStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户的亲友卡记录表
 */
@Entity
@Table(name="story_vip_friend_card")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VipFriendCard {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer srcType;

    Integer cardType;

    String nick;

    String srcInfo;

    Integer status;

    Date createTime;

    @JsonProperty("intro")
    public String getIntro() {
        return "备注：新用户才能领取故事哦！";
    }

    @JsonProperty("title")
    public String getTitle() {
        return "故事飞船亲友卡";
    }

    @JsonProperty("shareUrl")
    public String getShareUrl() {
        return MyEnv.env.getProperty("vip.friend.share.url") + id;
    }

    @JsonProperty("shareImg")
    public String getShareImg() {
        return "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/share2.jpg";
    }

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

    public Integer getSrcType() {
        return srcType;
    }

    public void setSrcType(UserAccountStyle srcType) {
        this.srcType = srcType.getId();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(VipFriendCardTypeStyle cardType) {
        this.cardType = cardType.getId();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSrcInfo() {
        return srcInfo;
    }

    public void setSrcInfo(String srcInfo) {
        this.srcInfo = srcInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(VipFriendCardStatusStyle status) {
        this.status = status.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
