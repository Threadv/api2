package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.config.MyEnv;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by wml on 2016/12/23.
 * 故事表
 */
@Entity
@Table(name="story_story")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
//@CmsTable(name = "故事",intro = "单本故事",deleteAble = false)
public class Story implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    @CmsColumn(name="名称",isShowToInner = true)
    String name;
    @JsonIgnore
    String pyName;
//    @CmsColumn(name="描述",isShowToList = false)
    String content;

    String shortContent;

    Long duration;

    @JsonIgnore
    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "cover/")
//    @CmsColumn(name="封面",inputType = CmsInputType.FILE,fileType = CmsFileType.IMAGE)
    String imgPath;

    String bigImg;

//    @CmsColumn(name="封面宽",isShowToList = false,isShowToAdd = false,defaultValue = "0",isShowToEdit = false)
    Integer coverWidth;
//    @CmsColumn(name="封面高",isShowToList = false,isShowToAdd = false,defaultValue = "0",isShowToEdit = false)
    Integer coverHeight;

//    @CmsColumn(isShowToList = false,isShowToAdd = false,isShowToEdit = false)
    @Transient
    String cover;//json 使用的url,返回拼接好的图片

    @Transient
    String bigCover;//json 使用的url,返回拼接好的图片

    @JsonIgnore
    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "appfile/")
//    @CmsColumn(name="app文件")
    String appFile;

//    @CmsColumn(isShowToList = false,isShowToAdd = false,isShowToEdit = false)
    @Transient
    String appUrl;//json 使用的url,返回拼接好的app

    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "webfile/")
//    @CmsColumn(name="网页文件",isShowToList = false,isShowToAdd = false,isShowToEdit = false)
    String webFile;
    @JsonIgnore
    @Column
//    @CmsStroge(saveType = CmsFieldFile.SaveType.NAME,strogeType= CmsFieldFile.StrogeType.ALIOSS,path = "previewfile/")
//    @CmsColumn(name="预览文件",isShowToList = false,isShowToAdd = false,isShowToEdit = false)
    String previewFile;
//    @CmsColumn(name="内容形式",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = StoryType.class)
    Integer type;

//    @CmsColumn(isShowToAdd = false,isShowToList = false,isShowToEdit = false)
//    @CmsColumn(name="故事集id",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = StoryCategory.class,isShowToAdd = false,isShowToEdit = false,isShowToList = false,defaultValue = "0",defaultValueType = CmsDefaultValueType.CUSTOM)
//    @Column(nullable = false,columnDefinition="int default1")
    Long serialStoryId;

    Long secondSerialStoryId;

    @Transient
    @JsonIgnore
//    @CmsColumn(isShowToAdd = false,isShowToList = false,isShowToEdit = false)
//    @ManyToOne(cascade = CascadeType.REFRESH,fetch= FetchType.LAZY)
//    @JoinColumn(name="serialStoryId",insertable=false,updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事集",inputType = CmsInputType.SELECT,isShowToAdd = false,isShowToEdit = false,isShowToList = false)
//    @Column(nullable = false,columnDefinition="int default1")
    SerialStory serialStory;

//    @CmsColumn(isShowToAdd = false,isShowToList = false,isShowToEdit = false)
    @Column(insertable=false,updatable = false)
    Integer categoryId;

    Integer categoryNewId;

    Integer ageType;

    @Transient
    String  categoryName;
    @Transient
    String  categoryIntro;

    @JsonIgnore
     @ManyToOne(cascade = CascadeType.REFRESH,fetch= FetchType.LAZY)
    @JoinColumn(name="categoryId",insertable=false,updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    DisplayGroup cateGroup;

    Integer isDel;

    /**
     * 在故事集中是否显示付费图标
     */
    @Transient
    Integer isShowSerialIcon;

//    //不再使用
//    @JsonIgnore
//    Integer iosPriceId;

    //不再使用
//    @JsonIgnore
//    @Transient
//    IOSPrice.Output iosPrice;

//    @Transient
////    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
//    AndroidPrice.Output androidPrice;

    //不再使用
//    @JsonIgnore
//    Integer androidPriceId;

//    @CmsColumn(name="免费",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = FreeStatus.class)
    Integer isFree;


    Integer status;


    Integer orderBy;

    //不再使用
//    @JsonIgnore
//    @Transient
////    @CmsColumn(name="单行本商店地址",isShowToList = false,isShowToEdit = false,isShowToAdd = false,defaultValue = "",defaultValueType = CmsDefaultValueType.CUSTOM)
//    String shopUrl;


    @Transient
//    @CmsColumn(name = "分享链接",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    String shareUrl;

    @Transient
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Integer isPurchased;//是否购买（1.5.0版+）
    @Transient
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Integer isBuy;

    @Transient
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Integer isNew;
//    @CmsColumn(name="非订阅是否可见",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = ShowStatusType.class)
    Integer isShow;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Date createTime;
//    @CmsColumn(name="发布时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Date onlineTime;
//    @CmsColumn(name="出版时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Date publishTime;

    /**
     *  订阅用户故事周刊列表里的发布时间是动态赋值的以保证多条记录显示不同数据
     */

    //    @CmsColumn(name="版权",isShowToList = false)
    String copyright;
//    @CmsColumn(name="翻译",isShowToList = false)
    String translator;
//    @CmsColumn(name="作者",isShowToList = false)
    String author;
//    @CmsColumn(name="配音",isShowToList = false)
    String dubber;
//    @CmsColumn(name="插画",isShowToList = false)
    String illustrator;
//    @CmsColumn(name=" 制作方",isShowToList = false)
    String producer;

//    @CmsColumn(name="版本号",defaultValue = "0",isShowToList = false)
    Integer ver;
//    @CmsColumn(name="语言（1中文2英文，逗号分隔）",defaultValue = "0",isShowToList = false)
    String language;


//    @Column
//    @ManyToMany(cascade={CascadeType.REFRESH},targetEntity = Label.class,fetch= FetchType.LAZY)
//    @JoinTable(name="story_label_story",    joinColumns={@JoinColumn(name="storyId",referencedColumnName="id")}
//        ,inverseJoinColumns={@JoinColumn(name="labelId",referencedColumnName="id")})
//    @CmsColumn(name="标签",inputType = CmsInputType.CHECKBOX,dataType = CmsDataType.DATABASE,isShowToList = false)
    @Transient
    List<Label> labels;


    //    @CmsColumn(name="所属分组关联",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
//    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },mappedBy="story")
    @Transient
    List<GroupRelevance> links;

//    @CmsColumn(name="期刊id",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    @Column(name="magazineId",insertable=false,updatable = false)
    Long magazineId;

//    @JsonIgnore
////    @CmsColumn(name="所属期刊",foreignName = "期刊中的故事",inputType = CmsInputType.SELECT,dataType = CmsDataType.DATABASE)
////    @CmsColumn(isShowToAdd = false,isShowToList = false,isShowToEdit = false)
//    @ManyToOne(cascade = CascadeType.REFRESH,fetch= FetchType.LAZY)
//    @JoinColumn(name="magazineId",insertable=true,updatable = true)
//    @NotFound(action= NotFoundAction.IGNORE)
//    Magazine magazine;


//    @CmsColumn(name="是否当期",inputType = CmsInputType.SELECT,dataType = CmsDataType.MAP,enumClassName = StoryNowType.class
//            ,isShowToEdit = false,isShowToAdd = false,defaultValue = "0")
    Integer isNow;
//    @CmsColumn(name="价格(分)")
    Integer price;

//    @CmsColumn(name="父级故事id",isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    @Column(name="parentId",insertable=false,updatable = false)
    Long parentId;

    @Transient
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false)
    Integer isAudio;//是否有音频

    Integer isParts;//是否有道具


    Integer scheduleType;//是否有道具

    String  onlineUrl;

    Integer wordCount;

    Integer vocabularyCount;

    /**
     * 是否购买系列
     */
    @Transient
    Integer isBuySerial;


    @JsonIgnore
//    @CmsColumn(isShowToAdd = false,isShowToEdit = false,isShowToList = false,defaultValue = "0")
    Integer storyId;

    Integer readWordCount;

    @Transient
    Integer isAbilityPlan;

    /**
     * 190815 增加
     * 用于app添加日志用，用于记录接口来源
     */
    @Transient
    Integer refererGroupId;

    public Integer getIsAbilityPlan() {
        return isAbilityPlan;
    }

    public void setIsAbilityPlan(Integer isAbilityPlan) {
        this.isAbilityPlan = isAbilityPlan;
    }

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="音频",inputType= CmsInputType.FORGIN,foreignName = "绘本故事关联的音频",isShowToAdd = false,isShowToEdit = false)
    Story story;

    public Integer getIsAudio() {
        //判断是否有音频
        if (  this.story==null ) {
            //没有音频，isAudio为0
            this.isAudio = 0;
        } else {
            this.isAudio = 1;
        }
        return isAudio;
    }

    @JsonProperty("isRecentUpdate")
    public Integer getRecentUpdate(){
        long now = System.currentTimeMillis();
        long lastWeek = now - 604800000;
        if (this.publishTime == null){
            return 0;
        }
        if(this.publishTime.getTime() > lastWeek){
            return 1;
        } else {
            return 0;
        }
    }

    @JsonProperty("isNew14")
    public Integer getIsNew14(){
        long now = System.currentTimeMillis();
        long twoLastWeek = now - 1209600000;
        if (this.publishTime == null){
            return 0;
        }
        if(this.publishTime.getTime() > twoLastWeek){
            return 1;
        } else {
            return 0;
        }
    }

    public Integer getReadWordCount() {
        return readWordCount;
    }

    public void setReadWordCount(Integer readWordCount) {
        this.readWordCount = readWordCount;
    }

    public void setIsAudio(Integer isAudio) {
        this.isAudio = isAudio;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
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

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortContent() {
        if(shortContent ==null){
            shortContent = "";
        }
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public Integer getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(Integer coverWidth) {
        this.coverWidth = coverWidth;
    }

    public Integer getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(Integer coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getAppFile() {
        return appFile;
    }

    public void setAppFile(String appFile) {
        this.appFile = appFile;
    }

    public String getWebFile() {
        return webFile;
    }

    public void setWebFile(String webFile) {
        this.webFile = webFile;
    }

    public String getPreviewFile() {
        return previewFile;
    }

    public void setPreviewFile(String previewFile) {
        this.previewFile = previewFile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public DisplayGroup getCateGroup() {
        return cateGroup;
    }

    public void setCateGroup(DisplayGroup cateGroup) {
        this.cateGroup = cateGroup;
    }

    public String getCategoryName() {
        if(this.cateGroup != null){
            categoryName = this.cateGroup.getName();
        }else{
            categoryName = "";
        }

        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIntro() {
        if(this.cateGroup != null){
            categoryIntro = this.cateGroup.getContent();
        }else{
            categoryIntro = "";
        }
        return categoryIntro;
    }

    public void setCategoryIntro(String categoryIntro) {
        this.categoryIntro = categoryIntro;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

//    public Integer getIosPriceId() {
//        return iosPriceId;
//    }
//
//    public void setIosPriceId(Integer iosPriceId) {
//        this.iosPriceId = iosPriceId;
//    }
//
//    public Integer getAndroidPriceId() {
//        return androidPriceId;
//    }
//
//    public void setAndroidPriceId(Integer androidPriceId) {
//        this.androidPriceId = androidPriceId;
//    }

    public Integer getIsFree() {
        if (this.isNow!=null&&this.isNow==1){
            //当期期刊免费的都处理成收费
            this.setIsFree(0);//收费
        }
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        createTime = this.publishTime;
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getIsBuy() {
        if(isBuy == null){
            isBuy = 0;
        }
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getIsPurchased() {
        if(isPurchased == null){
            isPurchased = 0;
        }
        return isPurchased;
    }

    public void setIsPurchased(Integer isPurchased) {
        this.isPurchased = isPurchased;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsNew() {
//        Date nowDate = new Date();
//        long  nowTimeStemp = nowDate.getTime();//当前时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(this.onlineTime==null){
            return 0;
        }
        long onlineTimeStemp = this.onlineTime.getTime();//发布时间
        long dv = System.currentTimeMillis()-onlineTimeStemp;
        if (  dv > (1000*3600*24*3) ) {
            //超出3天，不是新故事，isnew为0
            return 0;
        } else {
            return 1;
        }
//        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

//    public String getShopUrl() {
//        return shopUrl;
//    }
//
//    public void setShopUrl(String shopUrl) {
//        this.shopUrl = shopUrl;
//    }

    public Long getMagazineId() {
        if(magazineId == null){
            magazineId = 0L;
        }
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public Integer getIsNow() {
        return isNow;
    }

    public void setIsNow(Integer isNow) {
        this.isNow = isNow;
    }

    public Integer getIsParts() {
        return isParts;
    }

    public void setIsParts(Integer isParts) {
        this.isParts = isParts;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Story(){};
    public Story(Builder builder){
        this.id=builder.id;
        this.name=builder.name;
        this.content=builder.content;
        this.imgPath=builder.imgPath;
        this.bigImg=builder.bigImg;
        this.coverWidth = builder.coverWidth;
        this.coverHeight = builder.coverHeight;
        this.appFile=builder.appFile;
        this.webFile=builder.webFile;
        this.previewFile=builder.previewFile;
        this.type=builder.type;
        this.serialStory=builder.serialStory;
        this.serialStoryId=builder.serialStoryId;
        this.categoryId=builder.categoryId;
        this.isDel=builder.isDel;
//        this.iosPriceId=builder.iosPriceId;
//        this.androidPriceId=builder.androidPriceId;
        this.isFree=builder.isFree;
        this.status=builder.status;
        this.orderBy=builder.orderBy;
        this.createTime=builder.createTime;
        this.onlineTime=builder.onlineTime;
        this.publishTime=builder.publishTime;
//        this.shopUrl=builder.shopUrl;
        this.shareUrl=builder.shareUrl;
        this.copyright=builder.copyright;
        this.translator=builder.translator;
        this.author=builder.author;
        this.dubber=builder.dubber;
        this.illustrator=builder.illustrator;
        this.ver=builder.ver;
        this.cateGroup=builder.cateGroup;
        this.isNow=builder.isNow;
        this.isParts=builder.isParts;
        this.isShow=builder.isShow;
        this.price=builder.price;
        this.storyId=0;
    }


    public static class Builder{
        Long id;
        String name;
        String content;
        String imgPath;
        String bigImg;
        Integer coverWidth;
        Integer coverHeight;
        String appFile;
        String webFile;
        String previewFile;
        Integer type;
        Long serialStoryId;
        SerialStory serialStory;
        Integer categoryId;
        Integer isDel;
        Integer iosPriceId;
        Integer androidPriceId;
        Integer isFree;
        Integer status;
        Integer orderBy;
        Date createTime;
        Date onlineTime;
        Date publishTime;
        String shopUrl;
        String shareUrl;
        DisplayGroup cateGroup;

        String copyright;

        String translator;

        String author;

        String dubber;

        String illustrator;
        Integer ver;
        Integer isNow;
        Integer isParts;
        Integer isShow;
        Integer price;
        public Builder initAdd(){
//            user=new User();
            this.name="";
            this.createTime=new Date();
            this.onlineTime=new Date();
            this.publishTime=new Date();
            this.content="";
            this.imgPath=("");
            this.bigImg=("");
            this.coverWidth=(0);
            this.coverHeight=(0);
            this.type=(0);
            this.serialStory=null;
            this.appFile=("");
            this.webFile=("");
            this.previewFile=("");
            this.categoryId=0;
            this.isDel=iosPriceId=androidPriceId=0;
            this.isFree=status=orderBy=1;
            this.shopUrl=("");
            this.shareUrl=("");
            this.copyright=translator=author=dubber=illustrator="";
            this.serialStoryId=0L;
            this.ver=0;
            this.isNow=0;
            this.isParts=0;
            this.isShow=0;
            this.price=0;
            return this;
        }
        public Builder setName(String name){
            this.name=name;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setImgPath(String imgPath) {
            this.imgPath = imgPath;
            return this;
        }
        public Builder setBigImg(String bigImg) {
            this.bigImg = bigImg;
            return this;
        }
        public Builder setAppFile(String appFile) {
            this.appFile = appFile;
            return this;
        }

        public Builder setWebFile(String webFile) {
            this.webFile = webFile;
            return this;
        }

        public Builder setPreviewFile(String previewFile) {
            this.previewFile = previewFile;
            return this;
        }

        public Builder setType(Integer type) {
            this.type = type;
            return this;
        }

//        public Builder setSerialStoryId(Long serialStoryId) {
//            this.serialStoryId = serialStoryId;
//            return this;
//        }
        public Builder setSerialStory(SerialStory serialStory) {
            this.serialStory = serialStory;
            return this;
        }

        public Builder setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setIsDel(Integer isDel) {
            this.isDel = isDel;
            return this;
        }

        public Builder setIosPriceId(Integer iosPriceId) {
            this.iosPriceId = iosPriceId;
            return this;
        }

        public Builder setAndroidPriceId(Integer androidPriceId) {
            this.androidPriceId = androidPriceId;
            return this;
        }

        public Builder setIsFree(Integer isFree) {
            this.isFree = isFree;
            return this;
        }

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder setOrderBy(Integer orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }
        public Builder setCateGroup(DisplayGroup cateGroup) {
            this.cateGroup = cateGroup;
            return this;
        }


        public Builder setIsNow(Integer isNow) {
            this.isNow = isNow;
            return this;
        }

        public void setOnlineTime(Date onlineTime) {
            this.onlineTime = onlineTime;
        }

        public void setPublishTime(Date publishTime) {
            this.publishTime = publishTime;
        }

        public Story build() {
            return new Story(this);
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public void setShopUrl(String shopUrl) {
            this.shopUrl = shopUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }
    }

//    public IOSPrice.Output getIosPrice() {
//        if(iosPrice==null){
//            iosPrice=IOSPrice.getById(this.iosPriceId.intValue()).jsonObject();
//        }
//
//        return iosPrice;
//    }

//    public void setIosPrice(IOSPrice.Output iosPrice) {
//        this.iosPrice = iosPrice;
//    }
//
//    public AndroidPrice.Output getAndroidPrice() {
//        if(androidPrice==null){
//            androidPrice=AndroidPrice.getById(this.androidPriceId.intValue()).jsonObject();
//        }
//
//        return androidPrice;
//    }
//
//    public void setAndroidPrice(AndroidPrice.Output androidPrice) {
//        this.androidPrice = androidPrice;
//    }


//    public SerialStory getSerialStory() {
//        return serialStory;
//    }
//
//    public void setSerialStory(SerialStory serialStory) {
//        this.serialStory = serialStory;
//    }

//    public List<DisplayGroup> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<DisplayGroup> groups) {
//        this.groups = groups;
//    }

    public String getCover() {
        try{
            this.cover=MyEnv.env.getProperty("oss.url")+"cover/"+this.imgPath;
//            this.cover="http://oss.storybook.ifenghui.com/"+"cover/"+this.imgPath;

        }catch (Exception e){
            e.printStackTrace();
        }

        return cover;
    }

    public String getBigCover() {
        try{
            this.bigCover=MyEnv.env.getProperty("oss.url")+"cover/"+this.bigImg;
//            this.cover="http://oss.storybook.ifenghui.com/"+"cover/"+this.imgPath;
        }catch (Exception e){
            e.printStackTrace();
        }

        return bigCover;
    }

    public void setBigCover(String bigCover) {
        this.bigCover = bigCover;
    }
//    public void setCover(String cover) {
//        this.cover = cover;
//    }

    public String getAppUrl() {
//        if(this.type.intValue()==com.ifenghui.storybookapi.style.StoryType.GAME.getId()){
////            appUrl= CmsConfig.getMessage("http.url").replace("simplecms/","")+"web/shareStory/"+this.id;
//            this.appUrl=CmsConfig.getMessage("oss.url")+"appfolder/"+this.appFile.replace(".zip","/index.html");
//
//        }else{
        try{
            return MyEnv.env.getProperty("oss.url")+"appfile/"+this.appFile;
//            return "http://oss.storybook.ifenghui.com/"+"appfile/"+this.appFile;
        }catch (Exception e){
            return null;
        }


    }

//    public void setAppUrl(String appUrl) {
//        this.appUrl = appUrl;
//    }

    public String getShareUrl() {
        try{
//            return "http://storybook.ifenghui.com/"+"web/shareStory/"+this.id;
            return MyEnv.env.getProperty("http.url").replace("simplecms/","")
                    +"web/shareStory/"+this.id;
        }catch (Exception e){
            return null;
        }

//        this.shareUrl=CmsConfig.getMessage("oss.url")+"webfolder/"+this.webFile.replace(".zip","/index.html");
//        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Long getSerialStoryId() {
        return serialStoryId;
    }

    public void setSerialStoryId(Long serialStoryId) {
        this.serialStoryId = serialStoryId;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDubber() {
        return dubber;
    }

    public void setDubber(String dubber) {
        this.dubber = dubber;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
    @Override
    public String toString(){
        return this.name;
    }

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }

//    public Magazine getMagazine() {
//        return magazine;
//    }
//
//    public void setMagazine(Magazine magazine) {
//        this.magazine = magazine;
//    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Integer getIsBuySerial() {
        return isBuySerial;
    }

    public void setIsBuySerial(Integer isBuySerial) {
        this.isBuySerial = isBuySerial;
    }

    public Integer getIsShowSerialIcon() {
        return isShowSerialIcon;
    }

    public void setIsShowSerialIcon(Integer isShowSerialIcon) {
        this.isShowSerialIcon = isShowSerialIcon;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getVocabularyCount() {
        return vocabularyCount;
    }

    public void setVocabularyCount(Integer vocabularyCount) {
        this.vocabularyCount = vocabularyCount;
    }

    public Long getSecondSerialStoryId() {
        return secondSerialStoryId;
    }

    public void setSecondSerialStoryId(Long secondSerialStoryId) {
        this.secondSerialStoryId = secondSerialStoryId;
    }

    public Integer getCategoryNewId() {
        return categoryNewId;
    }

    public void setCategoryNewId(Integer categoryNewId) {
        this.categoryNewId = categoryNewId;
    }

    public Integer getAgeType() {
        return ageType;
    }

    public void setAgeType(Integer ageType) {
        this.ageType = ageType;
    }

    public Integer getRefererGroupId() {
        return refererGroupId;
    }

    public void setRefererGroupId(Integer refererGroupId) {
        this.refererGroupId = refererGroupId;
    }


}
