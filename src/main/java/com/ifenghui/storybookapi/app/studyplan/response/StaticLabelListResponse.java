package com.ifenghui.storybookapi.app.studyplan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class StaticLabelListResponse extends ApiResponse {

    Integer dayNumber;

    Integer storyCount;

    Integer wordCount;

    Integer readStoryDuration;
    Integer listenStoryDuration;
    Integer gameStoryDuration;

    @JsonProperty("isHasContent")
    public Integer getIsHasContent() {
        if(this.dayNumber == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    StaticLabel readWordCount;

    StaticLabel readDuration;

    StaticLabel readType;

    StaticLabel cognitionType;

    StaticLabel literacyType;

    StaticLabel fiveAreaType;

    GetShare getShare;

    public Integer getfiveAreaTypeLimit(){
        return 500;
    }

    public StaticLabel getReadWordCount() {
        return readWordCount;
    }

    public void setReadWordCount(StaticLabel readWordCount) {
        this.readWordCount = readWordCount;
    }

    public StaticLabel getReadDuration() {
        return readDuration;
    }

    public void setReadDuration(StaticLabel readDuration) {
        this.readDuration = readDuration;
    }

    public StaticLabel getReadType() {
        return readType;
    }

    public void setReadType(StaticLabel readType) {
        this.readType = readType;
    }

    public StaticLabel getCognitionType() {
        return cognitionType;
    }

    public void setCognitionType(StaticLabel cognitionType) {
        this.cognitionType = cognitionType;
    }

    public StaticLabel getLiteracyType() {
        return literacyType;
    }

    public void setLiteracyType(StaticLabel literacyType) {
        this.literacyType = literacyType;
    }

    public StaticLabel getFiveAreaType() {
        return fiveAreaType;
    }

    public void setFiveAreaType(StaticLabel fiveAreaType) {
        this.fiveAreaType = fiveAreaType;
    }

    public GetShare getGetShare() {
        return getShare;
    }

    public void setGetShare(GetShare getShare) {
        this.getShare = getShare;
    }

    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getReadStoryDuration() {
        return readStoryDuration;
    }

    public void setReadStoryDuration(Integer readStoryDuration) {
        this.readStoryDuration = readStoryDuration;
    }

    public Integer getListenStoryDuration() {
        return listenStoryDuration;
    }

    public void setListenStoryDuration(Integer listenStoryDuration) {
        this.listenStoryDuration = listenStoryDuration;
    }

    public Integer getGameStoryDuration() {
        return gameStoryDuration;
    }

    public void setGameStoryDuration(Integer gameStoryDuration) {
        this.gameStoryDuration = gameStoryDuration;
    }
}
