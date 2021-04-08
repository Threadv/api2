package com.ifenghui.storybookapi.app.express.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 物流中心订单
 */
@Entity
@Table(name="story_express_center_order")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class ExpressCenterOrder implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String title;

//   来源 1app，2微信，3其他
    Integer srcType;

    //来源备注
    String srcMark;

    Integer beginYear;

    Integer beginMonth;

    Integer endYear;

    Integer endMonth;

    Date orderTime;

    Date createTime;

    //是否在配送期间，1为在期间
    Integer isOpen;

    //外部订单号
    String outOrderId;

    //订单手机号
    String phone;

    //收件人名称
    String fullname;

    //地址
    String address;

    //省
    String province;

    //市
    String city;

    //区
    String district;

    //订阅总期数
    Integer volCount;

    //已经配送的期数
    Integer volOver;
//    是否是当前已绑号码
//    Integer isActive;

    public ExpressCenterOrder(){}

    public ExpressCenterOrder(AbilityPlanOrder abilityPlanOrder, ShopExpress shopExpress){
        this.setSrcStryle(ExpressSrcStyle.APP);
        this.srcMark="app订单导入";
        Calendar ca=Calendar.getInstance();
        ca.setTime(abilityPlanOrder.getSuccessTime());
        this.beginYear=ca.get(Calendar.YEAR);
        //判断购买时间是否是15号
        int data=ca.get(Calendar.DAY_OF_MONTH);
        this.beginMonth=ca.get(Calendar.MONTH)+1;

        if(data>14){
            ca.add(Calendar.MONTH,1);

            this.beginYear=ca.get(Calendar.YEAR);
            this.beginMonth=ca.get(Calendar.MONTH)+1;
        }
        this.title="故事飞船全年";
        this.endYear=ca.get(Calendar.YEAR)+1;
        this.endMonth=ca.get(Calendar.MONTH)+1;
        this.createTime=new Date();
        this.isOpen=1;
        this.outOrderId=abilityPlanOrder.getId()+"";
        this.srcType=ExpressSrcStyle.APP.getId();
        this.phone=shopExpress.getPhone();
        this.fullname=shopExpress.getReceiver();
        this.address=shopExpress.getArea()+" "+shopExpress.getAddress();
        if(shopExpress.getAddress().startsWith(shopExpress.getArea())){
            this.address=shopExpress.getAddress();
        }
        this.volCount=23;
        this.volOver=0;
        this.setProvince("");
        this.setCity("");
        this.district=shopExpress.getArea();
        this.setOrderTime(abilityPlanOrder.getSuccessTime());

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrcType() {
        return srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public void setSrcStryle(ExpressSrcStyle srcStryle) {
        this.srcType = srcStryle.getId();
    }

    public String getSrcMark() {
        return srcMark;
    }

    public void setSrcMark(String srcMark) {
        this.srcMark = srcMark;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(Integer beginMonth) {
        this.beginMonth = beginMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public Date getOrderTimeFormat(){
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getVolCount() {
        return volCount;
    }

    public void setVolCount(Integer volCount) {
        this.volCount = volCount;
    }

    public Integer getVolOver() {
        return volOver;
    }

    public void setVolOver(Integer volOver) {
        this.volOver = volOver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
