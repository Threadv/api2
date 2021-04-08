package com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 消息sms短信
 */
@Entity
@Table(name="story_activity_message_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SmsMessageDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer messageId;

    String phone;

    Date createTime;

    Integer isSend;

    String templateId;

    String code;

    @Transient
    SmsMessage smsMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }
}
