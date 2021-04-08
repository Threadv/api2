package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.SerialStoryStyle;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SerialStory 故事集
 * Created by wslhk on 2017/1/5.
 */
@Entity
@Table(name = "story_serial_story")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
//@CmsTable(name = "故事集",intro = "故事集")
public class SerialStory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    String intro;

    Integer storyCount;

    Integer price;

    Integer maxAmount;

    Integer type;

    Integer parentId;

    String shortIntro;

    Integer couponCount;

    String content;

    @JsonIgnore
    String banner;

    //故事集支持多图
    @Transient
    List<SerialBanner> banners;


    //是否会员免费，默认0不免费
    Integer memberFree;

    /**
     * 所属品牌，参考枚举
     * @see com.ifenghui.storybookapi.style.IpBrandStyle
     */
    Integer ipBrandId;

    @JsonProperty("banner")
    public String getBannerUrl() {
        if(banner==null){
            return "";
        }
        if(banner.indexOf("serial")!=-1){
            return MyEnv.env.getProperty("oss.url")  + banner;
        }
        return MyEnv.env.getProperty("oss.url") + "serial/" + banner;
    }

    @JsonProperty("bannerData")
    public String getBannerInner() {
        if(banner==null){
            return "";
        }
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @JsonProperty("iconData")
    String icon;

    @JsonProperty("icon")
    String geticonUrl() {
        if(icon.indexOf("serial")!=-1){
            return MyEnv.env.getProperty("oss.url")  + icon;
        }
        return MyEnv.env.getProperty("oss.url") + "serial/" + icon;
    }

//    @JsonIgnore
    Integer orderBy;
//    @CmsColumn(name="发布状态",dataType = CmsDataType.MAP,enumClassName = PublishStatus.class,inputType = CmsInputType.RADIO)

    Integer status;
    //    @CmsColumn(name="创建时间",isShowToAdd = false,isShowToEdit = false,defaultValueType = CmsDefaultValueType.DATE_NOW)
    @JsonIgnore
    Date createTime;

    @Transient
    Integer isBuy;

    String shareUrl;

    @JsonProperty("isMusic")
    public Integer getIsVideo() {
        if (this.id.equals(2L) || this.id.equals(14L)) {
            return 1;
        } else {
            return 0;
        }
    }

    @JsonProperty("labelList")
    public List<String> getLabelList() {
        List<String> labelList = new ArrayList<>();
        if (this.id.equals(13L)) {
            labelList.add("科学配餐");
            labelList.add("2~3岁");
        } else if (this.id.equals(14L)) {
            labelList.add("益智");
            labelList.add("4~6岁");
        }
        return labelList;
    }

//    @Transient
//    List<Story> storyList;

    public SerialStoryStyle getSerialStyle(){
        if(this.type==null){
            return null;
        }
        return SerialStoryStyle.getById(this.type);
    }
    String getSerialType() {

        if (this.type == 1) {
            return "ordinary";
        } else if (this.type == 3) {
            return "game";
        } else if (this.type == 4) {
            return "parentLesson";
        } else if (this.type == 6) {
            return "ip";
        } else if (this.type == 7) {
            return "audio";
        }
        return "";
    }

    @JsonProperty("ossImg")
    String getOssImg() {
        return MyEnv.env.getProperty("oss.url")+ "serial/index/" + getSerialType() + "/" + getSerialType() + "_" + this.id + ".png?tt=" + 100000123;
    }

    @JsonProperty("ossLongImg")
    String getOssLongImg() {
        return MyEnv.env.getProperty("oss.url")+"serial/index/" + getSerialType() + "/" + getSerialType() + "_" + this.id + "_long.png?tt=" + 100000123;
    }

    @JsonProperty("ossLongPadImg")
    String getOssLongPadImg() {
        return MyEnv.env.getProperty("oss.url")+"serial/index/" + getSerialType() + "/" + getSerialType() + "_" + this.id + "_long_pad.png?tt=" + 100000123;
    }

    @JsonProperty("ossPadImg")
    String getOssPadImg() {
        return MyEnv.env.getProperty("oss.url")+"serial/index/" + getSerialType() + "/" + getSerialType() + "_" + this.id + "_pad.png?tt=" + 100000123;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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

    public Integer getIsBuy() {
        if (isBuy == null) {
            isBuy = 0;
        }
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public List<SerialBanner> getBanners() {
        return banners;
    }

    public List<String> getBannerUrls() {
        if(banners==null){
            return new ArrayList<>();
        }
        List<String> bannerUrl=new ArrayList<>();
        for(SerialBanner banner:banners){
            bannerUrl.add(banner.getBannerUrl());
        }
        return bannerUrl;
    }

    public void setBanners(List<SerialBanner> banners) {
        this.banners = banners;
    }

    public Integer getMemberFree() {
        return memberFree;
    }

    public void setMemberFree(Integer memberFree) {
        this.memberFree = memberFree;
    }

    public Integer getIpBrandId() {
        return ipBrandId;
    }

    public void setIpBrandId(Integer ipBrandId) {
        this.ipBrandId = ipBrandId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
