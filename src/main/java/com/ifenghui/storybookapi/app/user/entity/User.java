package com.ifenghui.storybookapi.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.ifenghui.storybookapi.style.AbilityPlanStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.util.DateSerializer;


import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wslhk on 2016/12/20.
 */

/**
 * 用户表
 */
@Entity
@Table(name="story_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
//@CmsTable(name="用户账号管理",addAble = false)
public class User implements Serializable{



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id",isShowToInner = true)
    Long id;
//    @CmsColumn(name="昵称",isShowToInner = true,searchAble = true)
    String nick;
//    @CmsColumn(name="头像",inputType = CmsInputType.URL,fileType = CmsFileType.IMAGE,isShowToInner = false)
    String avatar;
//    @CmsColumn(name="性别",inputType = CmsInputType.SELECT,enumClassName = SexType.class,dataType = CmsDataType.MAP)
    Integer sex;
//    @JsonIgnore
//    @CmsColumn(name="生日")
    @JsonSerialize(using=DateSerializer.class)
    @JsonDeserialize(using=DateDeserializers.DateDeserializer.class)
    Date birthday;

    @Transient
    Integer age;

//    @CmsColumn(name="地址",isShowToList = false,isShowToAdd = false,isShowToEdit = false,defaultValueType = CmsDefaultValueType.STRING_EMPTY)
    String addr;

    @JsonIgnore
//    @CmsColumn(name="个性签名",isShowToList = false,isShowToAdd = false,isShowToEdit = false,defaultValueType = CmsDefaultValueType.STRING_EMPTY)
    @Column(name="instruction")
    String slogen;

//    @CmsColumn(isShowToInner = false,isShowToEdit = false,isShowToList = false,isShowToAdd = false)
    Integer vip;

    @JsonIgnore
    Integer svip;//超级vip，后台添加，有权限查看所有收费内容
//    @CmsColumn(name="主账号类型手机号0微信1")
    Integer isAccount;

//    @CmsColumn(name="是否游客1是0否")
    Integer isTourist;

    Integer readDays;

    Integer readCount;

//    @JsonIgnore
    Integer isTest;//是否测试账户1是0否")

    @JsonIgnore
    String channel;

    /**
     * 是否注销标记，1：注销 0：正常
     */
//    @JsonIgnore
    Integer unsubscribe;

    @Transient
//    @CmsColumn(name="余额")
    Integer balance;

    @Transient
//    @CmsColumn(name="阅读星")
    Integer starCount;

    Integer clockSeveralCount;

    Integer clockMaxSeveralCount;

    @JsonIgnore
    String ipAddress;

    Integer isAbilityPlan;

    @Transient
    @JsonProperty("suid")
    String getUserSuid(){
        if(this.createTime==null){
            return "";
        }
        int suid =(int) (this.id + 123);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy");
        String nowYear = simpleDateFormat.format(this.getCreateTime());
        return "s" + nowYear + suid;
    }

    @JsonIgnore
    String province;

    @JsonIgnore
    String city;

    public Integer getClockSeveralCount() {
        return clockSeveralCount;
    }

    public void setClockSeveralCount(Integer clockSeveralCount) {
        this.clockSeveralCount = clockSeveralCount;
    }

    public Integer getClockMaxSeveralCount() {
        return clockMaxSeveralCount;
    }

    public void setClockMaxSeveralCount(Integer clockMaxSeveralCount) {
        this.clockMaxSeveralCount = clockMaxSeveralCount;
    }

    public Integer getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

//    @CmsColumn(name="电话")
    String phone;
    @JsonIgnore
//    @CmsColumn(name="密码",isShowToList = false)
    String password;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,countAble = true)
    Date createTime;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStarCount() {
        if(starCount == null){
            starCount = 0;
        }
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public User(){

    }
    public User(Builder builder){
        this.id=builder.id;
        this.nick=builder.nick;
        this.avatar=builder.avatar;
        this.sex=builder.sex;
        this.birthday=builder.birthday;
        this.addr=builder.addr;
        this.slogen=builder.slogen;
        this.vip=builder.vip;
        this.isAccount=builder.isAccount;
        this.isTourist=builder.isTourist;
        this.phone=builder.phone;
        this.password=builder.password;
        this.createTime=builder.createTime;
        this.channel=builder.channel;
        this.city="";
        this.province="";
        this.clockMaxSeveralCount=0;
        this.clockSeveralCount=0;
        this.isAbilityPlan=0;
        this.ipAddress="";
        this.isTest=1;
        this.readCount=0;
        this.readDays=0;
        this.svip=0;
        this.unsubscribe=0;
        this.addr="";
    }
    public static class Builder{
        Long id;
        String nick;
        String avatar;
        Integer sex;
        Date birthday;
        String addr;
        String slogen;
        Integer vip;
        Integer isAccount;
        Integer isTourist;
        String phone;
        String password;
        Date createTime;
        String channel;

        public Builder initAdd(){
//            user=new User();
            this.nick="";
            this.createTime=new Date();
            this.phone="";
            this.password=("");
            this.vip=(0);
            this.isAccount=(0);
            this.isTourist=(0);
            this.sex=(0);
            this.addr=("");
//            this.avatar=("");
            this.avatar=("http://storybook.oss-cn-hangzhou.aliyuncs.com/avatar/useravatar.png");
            this.slogen=("");
            this.birthday=(new Date());
            this.channel="";

            return this;
        }
        public Builder setSlogen(String slogen){
            this.slogen=slogen;
            return this;
        }
        public Builder setCreateTime(Date createTime){
            this.createTime=createTime;
            return this;
        }

        public Builder setBirthday(Date birthday){
            this.birthday=birthday;
            return this;
        }

        public Builder setNick(String nick){
            this.nick=nick;
            return this;
        }
        public Builder setPhone(String phone){
            this.phone=phone;
            return this;
        }
        public Builder setPassword(String password){
            this.password=password;
            return this;
        }
        public Builder setVip(Integer vip){
            this.vip=vip;
            return this;
        }
        public Builder setIsAccount(Integer isAccount){
            this.isAccount=isAccount;
            return this;
        }

        public Builder setIsTourist(Integer isTourist) {
            this.isTourist = isTourist;
            return this;
        }

        public Builder setAvatar(String avatar){
            this.avatar=avatar;
            return this;
        }
        public Builder setAddr(String addr){
            this.addr=addr;
            return this;
        }
        public Builder setSex(Integer sex){
            this.sex=sex;
            return this;
        }
        public User build() {
            return new User(this);
        }

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
//        int i = avatar.lastIndexOf("/");
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {

        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSlogen() {
        return slogen;
    }

    public void setSlogen(String slogen) {
        this.slogen = slogen;
    }

    public Integer getIsTourist() {
        return isTourist;
    }

    public void setIsTourist(Integer isTourist) {
        this.isTourist = isTourist;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getSvip() {
        return svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
    public String toString(){return this.nick;}

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Integer getReadDays() {
        return readDays;
    }

    public void setReadDays(Integer readDays) {
        this.readDays = readDays;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getUnsubscribe() {
        return unsubscribe;
    }

    public void setUnsubscribe(Integer unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    @JsonIgnore
    public Integer getIsAbilityPlan() {
        return isAbilityPlan;
    }

    //兼容svip用于展示 阅读权限
    @JsonProperty("isAbilityPlan")
    public Integer getIsAbilityPlanAndVip() {
        if((this.svip!=null)&&(this.getSvip()>0)){
            return 1;
        }
        return isAbilityPlan;
    }

    public void setIsAbilityPlan(Integer isAbilityPlan) {
        this.isAbilityPlan = isAbilityPlan;
    }

    public boolean isSvipLevelThreeAndFour() {
        if(this.getSvip().equals(SvipStyle.LEVEL_THREE.getId()) || this.getSvip().equals(SvipStyle.LEVEL_FOUR.getId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAbilityPlan() {
        if(this.getIsAbilityPlan() ==1 ) {
            return true;
        } else {
            return false;
        }
    }
}
