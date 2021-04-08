package com.ifenghui.storybookapi.app.app.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.config.MyEnv;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_ver_channel")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="版本渠道关联管理",addAble = false)
/**
 * Created by wml on 2017/4/10
 * 版本和渠道关联表
 */
public class VerChannel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    @Column(updatable = false)
//    @CmsColumn(name="版本id")
//    Long ver_id;
    @Column(updatable = false)
//    @CmsColumn(name="渠道id")
    Long channelId;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "verId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="版本",inputType= CmsInputType.FORGIN,foreignName = "版本")
    Ver ver;

    Integer verId;

    @Column(updatable = false)
//    @CmsColumn(name="渠道包下载地址")
    String url;

    @JsonIgnore
    Integer appId;

    @JsonIgnore
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ver getVer() {
        return ver;

    }
    public void setVer(Ver ver) {
        this.ver = ver;
        if(ver != null) {
            this.verId = ver.getId().intValue();
        }
    }
    public String getUrl() {

        try{
            //oss.url=http://oss.storybook.ifenghui.com/
            return MyEnv.env.getProperty("oss.url")+"pkg/"+this.url;
        }catch (Exception e){
            return null;
        }


//        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
