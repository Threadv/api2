package com.ifenghui.storybookapi.app.system.service.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.Date;

/**
 * Created by wslhk on 2017/11/14.
 */
public class StoryJson extends Story {

    public StoryJson(){

    }

    public StoryJson(Story story){
        this.setId(story.getId());
        this.setName(story.getName());
        this.setPyName(story.getPyName());
        this.setContent(story.getContent());
        this.setShortContent(story.getShortContent());
        this.setDuration(story.getDuration());
        this.setBigImg(story.getBigImg());
        this.setCover(story.getCover());
        this.setCoverWidth(story.getCoverWidth());
        this.setCoverHeight(story.getCoverHeight());
        this.setBigCover(story.getBigCover());
        this.setAppUrl(story.getAppUrl());
        this.setWebFile(story.getWebFile());
        this.setType(story.getType());
        this.setSerialStoryId(story.getSerialStoryId());
        this.setSecondSerialStoryId(story.getSecondSerialStoryId());
        this.setCategoryId(story.getCategoryId());
        this.setCategoryNewId(story.getCategoryNewId());
        this.setAgeType(story.getAgeType());
        this.setCategoryName(story.getCategoryName());
        this.setCategoryIntro(story.getCategoryIntro());
        this.setIsDel(story.getIsDel());
        this.setIsShowSerialIcon(story.getIsShowSerialIcon());
//        this.setIosPrice(story.getIosPrice());
//        this.setAndroidPrice(story.getAndroidPrice());
        this.setIsFree(story.getIsFree());
        this.setStatus(story.getStatus());
        this.setOrderBy(story.getOrderBy());
//        this.setShopUrl(story.getShopUrl());
        this.setShareUrl(story.getShareUrl());
        this.setIsPurchased(story.getIsPurchased());
        this.setIsBuy(story.getIsBuy());
        this.setIsNew(story.getIsNew());
        this.setIsShow(story.getIsShow());
        this.setCreateTime(story.getCreateTime());
        this.setOnlineTime(story.getOnlineTime());
        this.setPublishTime(story.getPublishTime());
        this.setCopyright(story.getCopyright());
        this.setTranslator(story.getTranslator());
        this.setAuthor(story.getAuthor());
        this.setDubber(story.getDubber());
        this.setIllustrator(story.getIllustrator());
        this.setProducer(story.getProducer());
        this.setVer(story.getVer());
        this.setLanguage(story.getLanguage());
        this.setLabels(story.getLabels());
        this.setMagazineId(story.getMagazineId());
        this.setIsNow(story.getIsNow());
        this.setPrice(story.getPrice());
        this.setParentId(story.getParentId());
        this.setIsAudio(story.getIsAudio());
        this.setIsParts(story.getIsParts());
        this.setScheduleType(story.getScheduleType());
        this.setOnlineUrl(story.getOnlineUrl());
        this.setWordCount(story.getWordCount());
        this.setVocabularyCount(story.getVocabularyCount());
        this.setIsBuySerial(story.getIsBuySerial());
        this.setReadWordCount(story.getReadWordCount());
        this.setIsAbilityPlan(story.getIsAbilityPlan());
        this.setStory(story.getStory());

    }

    @JsonFormat(timezone = "GMT+8", pattern = "dd/MMM/yyyy:HH:mm:ss Z")
    Date createTime;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
