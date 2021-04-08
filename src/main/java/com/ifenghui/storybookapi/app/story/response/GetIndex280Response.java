package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.CustomGroup;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.story.response.lesson.LessonIndex;
import java.util.List;

public class GetIndex280Response extends ApiResponse {

    List<Ads> ads;
    /** 精品推荐*/
    CustomGroup recommendGroup;
    /** 最新故事*/
    List<DisplayGroup> newStoryGroup;
    /** 精品课程 */
    LessonIndex lessonIndex;
    /** 故事足迹*/
    CustomGroup userReadRecordGroup;
    /**飞船电台 */
    CustomGroup radioGroup;
    /**聆听专区 */
    CustomGroup audioSerialGroup;
    /**IP专区 */
    CustomGroup ipStoryGroup;
    /**大咖专区（家长课） */
    CustomGroup parentLessonGroup;
    /**经典故事 && 创意思维 */
    List<DisplayGroup> classicAndCreateGroup;
    /**绘本丛书 */
    CustomGroup serialGroup;
    /**情感教育 &&幽默故事 */
    List<DisplayGroup> emotionAndHumourGroup;
    /**益智训练专区 */
    CustomGroup gameSerialGroup;
    /**传统文化 */
    List<DisplayGroup> traditionCultureGroup;
    /**其他分类 */
    List<DisplayGroup> otherGroup;


    Integer isSvip;

    Prompt prompt;

    Integer serialStoryCount;

    public CustomGroup getAudioSerialGroup() {
        return audioSerialGroup;
    }

    public void setAudioSerialGroup(CustomGroup audioSerialGroup) {
        this.audioSerialGroup = audioSerialGroup;
    }

    public List<Ads> getAds() {
        return ads;
    }

    public void setAds(List<Ads> ads) {
        this.ads = ads;
    }

    public CustomGroup getRecommendGroup() {
        return recommendGroup;
    }

    public void setRecommendGroup(CustomGroup recommendGroup) {
        this.recommendGroup = recommendGroup;
    }

    public List<DisplayGroup> getNewStoryGroup() {
        return newStoryGroup;
    }

    public void setNewStoryGroup(List<DisplayGroup> newStoryGroup) {
        this.newStoryGroup = newStoryGroup;
    }

    public LessonIndex getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(LessonIndex lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public CustomGroup getUserReadRecordGroup() {
        return userReadRecordGroup;
    }

    public void setUserReadRecordGroup(CustomGroup userReadRecordGroup) {
        this.userReadRecordGroup = userReadRecordGroup;
    }

    public CustomGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(CustomGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public CustomGroup getIpStoryGroup() {
        return ipStoryGroup;
    }

    public void setIpStoryGroup(CustomGroup ipStoryGroup) {
        this.ipStoryGroup = ipStoryGroup;
    }

    public List<DisplayGroup> getClassicAndCreateGroup() {
        return classicAndCreateGroup;
    }

    public void setClassicAndCreateGroup(List<DisplayGroup> classicAndCreateGroup) {
        this.classicAndCreateGroup = classicAndCreateGroup;
    }

    public List<DisplayGroup> getEmotionAndHumourGroup() {
        return emotionAndHumourGroup;
    }

    public void setEmotionAndHumourGroup(List<DisplayGroup> emotionAndHumourGroup) {
        this.emotionAndHumourGroup = emotionAndHumourGroup;
    }

    public CustomGroup getSerialGroup() {
        return serialGroup;
    }

    public void setSerialGroup(CustomGroup serialGroup) {
        this.serialGroup = serialGroup;
    }

    public CustomGroup getGameSerialGroup() {
        return gameSerialGroup;
    }

    public void setGameSerialGroup(CustomGroup gameSerialGroup) {
        this.gameSerialGroup = gameSerialGroup;
    }

    public List<DisplayGroup> getOtherGroup() {
        return otherGroup;
    }

    public void setOtherGroup(List<DisplayGroup> otherGroup) {
        this.otherGroup = otherGroup;
    }

    public Integer getIsSvip() {
        return isSvip;
    }

    public void setIsSvip(Integer isSvip) {
        this.isSvip = isSvip;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public Integer getSerialStoryCount() {
        return serialStoryCount;
    }

    public void setSerialStoryCount(Integer serialStoryCount) {
        this.serialStoryCount = serialStoryCount;
    }

    public List<DisplayGroup> getTraditionCultureGroup() {
        return traditionCultureGroup;
    }

    public void setTraditionCultureGroup(List<DisplayGroup> traditionCultureGroup) {
        this.traditionCultureGroup = traditionCultureGroup;
    }

    public CustomGroup getParentLessonGroup() {
        return parentLessonGroup;
    }

    public void setParentLessonGroup(CustomGroup parentLessonGroup) {
        this.parentLessonGroup = parentLessonGroup;
    }
}
