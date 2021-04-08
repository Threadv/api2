package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.user.entity.User;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2018/11/13 16:00
 * @Description:
 */
public class GetAbilityPlanPrivilegeResponse extends BaseResponse{


    /**检测券用真实不需要随便赋值*/
    Integer targetValue;
    Integer iosIsCheck;
    Integer price;
    @Transient
    User user;
    String content;

    Date starTime;
    Date endTime;

    List<RightIntro> intros;

//    List<RightIntro> introsOnline;

    String productIntro;

    //全部权益
    BaobaoPrice baobaoAllPrice;
    //线上权益价格
    BaobaoPrice baobaoOnlinePrice;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIosIsCheck() {
        return iosIsCheck;
    }

    public void setIosIsCheck(Integer iosIsCheck) {
        this.iosIsCheck = iosIsCheck;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<RightIntro> getIntros() {
        return intros;
    }

    public void setIntros(List<RightIntro> intros) {
        this.intros = intros;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public BaobaoPrice getBaobaoAllPrice() {
        return baobaoAllPrice;
    }

    public void setBaobaoAllPrice(BaobaoPrice baobaoAllPrice) {
        this.baobaoAllPrice = baobaoAllPrice;
    }

    public BaobaoPrice getBaobaoOnlinePrice() {
        return baobaoOnlinePrice;
    }

    public void setBaobaoOnlinePrice(BaobaoPrice baobaoOnlinePrice) {
        this.baobaoOnlinePrice = baobaoOnlinePrice;
    }

//    public List<RightIntro> getIntrosOnline() {
//        return introsOnline;
//    }
//
//    public void setIntrosOnline(List<RightIntro> introsOnline) {
//        this.introsOnline = introsOnline;
//    }

}
