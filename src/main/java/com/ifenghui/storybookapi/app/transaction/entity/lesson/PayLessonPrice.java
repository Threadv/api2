package com.ifenghui.storybookapi.app.transaction.entity.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * 课程购买价格配置表
 */

@Entity
@Table(name="story_pay_lesson_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayLessonPrice implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @JsonIgnore
    Integer lessonItemCount;

    @Transient
    Integer lessonNum;

    @Transient
    Integer lessonId;

    Integer orderBy;

    @JsonIgnore
    Integer discount;

    @Transient
    Integer orderDiscount;

    @JsonIgnore
    String icon;

    @Transient
    String getIconUrl(){
        return MyEnv.env.getProperty("oss.url") + "lesson/" + icon;
    }

    String intro;

    String name;

    Integer status;

    @Transient
    Integer isCan;

    @Transient
    Integer price;

    @Transient
    String orderDiscountStr;

    @Transient
    Integer isRecommand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLessonItemCount() {
        return lessonItemCount;
    }

    public void setLessonItemCount(Integer lessonItemCount) {
        this.lessonItemCount = lessonItemCount;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsCan() {
        return isCan;
    }

    public void setIsCan(Integer isCan) {
        this.isCan = isCan;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Integer getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(Integer orderDiscount) {
        this.orderDiscount = orderDiscount;
    }


    public String getOrderDiscountStr() {

        if(orderDiscount != null){
            if(orderDiscount.equals(90)){
                this.orderDiscountStr = "9折";
            } else if(orderDiscount.equals(85)){
                this.orderDiscountStr = "8.5折";
            } else if(orderDiscount.equals(80)) {
                this.orderDiscountStr = "8折";
            } else if(orderDiscount.equals(75)){
                this.orderDiscountStr = "7.5折";
            } else if(orderDiscount.equals(70)){
                this.orderDiscountStr = "7折";
            } else if(orderDiscount.equals(60)) {
                this.orderDiscountStr = "6折";
            } else if(id.equals(6)){
                this.orderDiscountStr = "特惠";
            } else {
                this.orderDiscountStr = "";
            }
        }

        return orderDiscountStr;
    }

    public void setOrderDiscountStr(String orderDiscountStr) {
        this.orderDiscountStr = orderDiscountStr;
    }

    public Integer getIsRecommand() {
        if(this.id.equals(4)){
            this.isRecommand = 1;
        } else {
            this.isRecommand = 0;
        }
        return isRecommand;
    }

    public void setIsRecommand(Integer isRecommand) {
        this.isRecommand = isRecommand;
    }
}
