package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 故事的故事包表
 */
@Entity
@Table(name="story_story_package")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "故事包管理",intro = "故事包")
public class StoryPackage implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;


//    @JsonIgnore
//    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "cover/")
//    @CmsColumn(name="封面",inputType = CmsInputType.FILE,fileType = CmsFileType.IMAGE)
//    String imgPath;


    @JsonIgnore
    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "appfile/")
//    @CmsColumn(name="app文件")
    String appFile;

    @JsonIgnore
    Integer engineType;//客户端引擎版本

//    @Transient
//    String appUrl;//json 使用的url,返回拼接好的app

//    @JsonIgnore
//    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "webfile/")
//    @CmsColumn(name="网页文件",isShowToList = false,isShowToAdd = false,isShowToEdit = false)
//    String webFile;

//    @JsonIgnore
//    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "previewfile/")
//    @CmsColumn(name="预览文件",isShowToList = false,isShowToAdd = false,isShowToEdit = false)
//    String previewFile;


//    @Column(nullable = true)
//    @CmsColumn(name="是否当前使用",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class
//    ,defaultValue = "0",isShowToAdd = false,isShowToEdit = false)
    Integer status;

    @JsonIgnore
//    @CmsColumn(name="创建时间",defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    @JsonIgnore
    Integer storyId;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事",inputType = CmsInputType.FORGIN,isShowToAdd = true,isShowToEdit = false)
    Story story;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppFile() {

        return appFile;
    }

    public void setAppFile(String appFile) {
        this.appFile = appFile;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Integer getEngineType() {
        return engineType;
    }

    public void setEngineType(Integer engineType) {
        this.engineType = engineType;
    }
    //    public Integer getStoryId() {
//        return storyId;
//    }
//
//    public void setStoryId(Integer storyId) {
//        this.storyId = storyId;
//    }
//    public String getAppUrl() {
//        try{
//            return MyEnv.env.getProperty("oss.url")+"appfile/"+this.appFile;
//        }catch (Exception e){
//            return null;
//        }
//    }
//    public void setAppUrl(String appUrl) {
//        this.appUrl = appUrl;
//    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }
}
