package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbilityPlanPrice {

    String title;

    String name;

    String content;

    Integer days;

    Integer price;

    Integer weekPlanStyle;

    Integer isCheck;

    Integer realPrice;

    Integer buyNum;

    /** 是否显示优惠价格*/
    Integer isShow;

    String  pre;
    String  last;
    Integer onlineOnly;

    String imgPath;

    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @JsonProperty("iosSubPrice")
    public String getIosSubPrice() {
        return "subscription_auto_a_year";
    }


    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public AbilityPlanPrice() {
    }

    public AbilityPlanPrice(String title, String name,String content, int days, int price, int weekPlanStyle,int isCheck,int realPrice,int buyNum,int isShow,int onlineOnly) {
        this.title = title;
        this.name = name;
        this.content=content;
        this.days = days;
        this.price = price;
        this.weekPlanStyle = weekPlanStyle;
        this.isCheck = isCheck;
        this.realPrice=realPrice;
        this.buyNum=buyNum;
        this.isShow=isShow;
        this.onlineOnly=onlineOnly;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(Integer weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }

    public Integer getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
