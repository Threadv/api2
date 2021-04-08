package com.ifenghui.storybookapi.app.express.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 物流中心订单
 */
@Entity
@Table(name="story_express_center_track")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class ExpressCenterTrack implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

//   物流公司id 1圆通,2中通，3韵达
    Integer trackType;

    //
    String trackNo;

    Date createTime;

    Integer centerOrderId;

    Integer isSend;

    Date sendTime;

    Integer volCount;

    Integer year;

    Integer month;
//冗余订单表的状态，如果订单已完成，所有状态全改变
    Integer orderIsOpen;

    //收货人
    String fullname;

    //联系方式
    String phone;

    //订购时间
    Date orderTime;

    //   来源 1app，2微信，3其他
    Integer srcType;

    //来源说明
    String srcMark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrackType() {
        return trackType;
    }

    public void setTrackType(Integer trackType) {
        this.trackType = trackType;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
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

    public Integer getCenterOrderId() {
        return centerOrderId;
    }

    public void setCenterOrderId(Integer centerOrderId) {
        this.centerOrderId = centerOrderId;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Date getSendTime() {
        return sendTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSendTimeFormat(){
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getVolCount() {
        return volCount;
    }

    public void setVolCount(Integer volCount) {
        this.volCount = volCount;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getOrderIsOpen() {
        return orderIsOpen;
    }

    public void setOrderIsOpen(Integer orderIsOpen) {
        this.orderIsOpen = orderIsOpen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public Date getOrderTimeFormat(){
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime; }

    public Integer getSrcType() {
        return srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public String getSrcMark() {
        return srcMark;
    }

    public void setSrcMark(String srcMark) {
        this.srcMark = srcMark;
    }
}
