package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2017/1/9.
 */

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.User;

import javax.persistence.Transient;
import java.util.Date;

public class SettingResponse extends ApiResponse {
    User user;
    Integer isSubscribe;
    Integer couponCount;
    Integer mixCouponCount;
    String ShareExplain;
    Integer inPromotion;
    Integer isCheck;
    Integer isShowSubscription;
    Integer startReadDayNumber;
    Integer storyNumber;

    Integer wordCount;
    Integer androidBalance;
    Integer iosBalance;
    Integer isPhoneBind;

    /**宝宝会读（优能计划）截止时间 */
    Date endTime;

    /**常见问题*/
    String questionUrl;

    /**是否绑定过支付宝*/
    Integer isBandAli;

    public Integer getIsBandAli() {
        return isBandAli;
    }

    public void setIsBandAli(Integer isBandAli) {
        this.isBandAli = isBandAli;
    }

    public String getQuestionUrl() {
        if(questionUrl == null){
            return "";
        }
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getAndroidBalance() {
        return androidBalance;
    }

    public void setAndroidBalance(Integer androidBalance) {
        this.androidBalance = androidBalance;
    }

    public Integer getIosBalance() {
        return iosBalance;
    }

    public void setIosBalance(Integer iosBalance) {
        this.iosBalance = iosBalance;
    }

    public Integer getIsPhoneBind() {
        return isPhoneBind;
    }

    public void setIsPhoneBind(Integer isPhoneBind) {
        this.isPhoneBind = isPhoneBind;
    }

    public Integer getInPromotion() {
        return inPromotion;
    }

    public void setInPromotion(Integer inPromotion) {
        this.inPromotion = inPromotion;
    }

    public String getShareExplain() {
        return ShareExplain;
    }

    public void setShareExplain(String shareExplain) {
        ShareExplain = shareExplain;
    }

    GetSettingUserAccountResponse account;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }


    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public Integer getMixCouponCount() {
        return mixCouponCount;
    }

    public void setMixCouponCount(Integer mixCouponCount) {
        this.mixCouponCount = mixCouponCount;
    }

    public GetSettingUserAccountResponse getAccount() {
        return account;
    }

    public void setAccount(GetSettingUserAccountResponse account) {
        this.account = account;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getIsShowSubscription() {
        return isShowSubscription;
    }

    public void setIsShowSubscription(Integer isShowSubscription) {
        this.isShowSubscription = isShowSubscription;
    }

    public Integer getStartReadDayNumber() {
        return startReadDayNumber;
    }

    public void setStartReadDayNumber(Integer startReadDayNumber) {
        this.startReadDayNumber = startReadDayNumber;
    }

    public Integer getStoryNumber() {
        return storyNumber;
    }

    public void setStoryNumber(Integer storyNumber) {
        this.storyNumber = storyNumber;
    }
}
