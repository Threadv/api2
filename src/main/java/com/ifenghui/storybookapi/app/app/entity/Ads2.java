package com.ifenghui.storybookapi.app.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.AdPositionStyle;
import com.ifenghui.storybookapi.util.VersionUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wml on 2016/12/27.
 */

/**
 * 首页banner表
 */
@Entity
@Table(name="story_ads")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ads2 implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

//    @CmsColumn(name="标题")
    String title;

//    @CmsColumn(name="内容")
    String content;

//    @CmsColumn(name="图片")
    @JsonIgnore
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "banner/")
//    @CmsColumn(name="banner",inputType = CmsInputType.FILE,fileType = CmsFileType.IMAGE)
    String imgPath;


    String icon;

//    @Transient
////    @CmsColumn(isShowToAdd = false,isShowToList = false,isShowToEdit = false)
//    String banner;

//    @CmsColumn(name="广告类型",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = AdTargetType.class)
    Integer targetType;

//    @CmsColumn(name="目标id",intro="分组id/故事id/故事集id")
    Integer targetValue;

//    @CmsColumn(name="外链")
    String url;

//    @CmsColumn(name="发布状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;

//    @CmsColumn(name="排序",defaultValue = "0")
    Integer orderBy;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

//    @Transient
//    List<Label> labels;
    @JsonIgnore
    Integer isIosVisual;

    @JsonIgnore
    Integer adsPosition;


    @Transient
    String padImg;

    public String getPadImg() {
        return padImg;
    }

    public void setPadImg(String padImg) {
        this.padImg = padImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getTargetType() {

//        if(url!=null&&url.startsWith("storyship")&&!VersionUtil.isAllow(MyEnv.ver, "2.5.0")){
//            return 100;
//        }

        //设置跳转类型为5不做跳转
        if(url==null||url.equals("")){
            return 5;
        }
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetValue() {
//        if(MyEnv.platfrom== StoryConfig.Platfrom.ANDROID&&!VersionUtil.isAllow(MyEnv.ver, "2.5.0")){
//            return 3;
//        }

        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public String getUrl() {
        if(MyEnv.ver == null || url.startsWith("http")){
            return url;
//        } else if(MyEnv.platfrom== StoryConfig.Platfrom.IOS){
//            return url;
        } else if(!VersionUtil.isAllow(MyEnv.ver, "2.5.0")&&url.startsWith("storyship://story?method=SS_OpenSVip")){
            return "https://storyship.ifenghui.com/public/index.php/editor/pagemake/pagemake.html?id=58";
        } else {
            return url;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsIosVisual() {
        return isIosVisual;
    }

    public void setIsIosVisual(Integer isIosVisual) {
        this.isIosVisual = isIosVisual;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBanner() {
//        oss.url=http://oss.storybook.ifenghui.com/
        if(this.imgPath.indexOf("banner")!=-1){
            return MyEnv.env.getProperty("oss.url")+this.imgPath;
        }
        return MyEnv.env.getProperty("oss.url")+"banner/"+this.imgPath;
    }

//    public void setBanner(String banner) {
//        this.banner = banner;
//    }

    public String getIcon() {
        return MyEnv.env.getProperty("oss.url")+"banner/"+this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAdsPosition() {
        return adsPosition;
    }

    public void setAdsPosition(Integer adsPosition) {
        this.adsPosition = adsPosition;
    }

    public void setAdPositionStyle(AdPositionStyle adPositionStyle){
        this.adsPosition=adPositionStyle.getId();
    }

    public AdPositionStyle getAdPositionStyle(){
        return AdPositionStyle.getById(this.adsPosition);
    }
}
