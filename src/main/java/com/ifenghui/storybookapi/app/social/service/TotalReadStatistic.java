package com.ifenghui.storybookapi.app.social.service;

public class TotalReadStatistic {

    Integer TotalReadStoryNumber;

    Integer TotalReadStoryDuration;

    Integer TotalListenStoryNumber;

    Integer TotalListenStoryDuration;

    Integer TotalGameStoryNumber;

    Integer TotalGameStoryDuration;

    Integer wordCount;

    Integer vocabularyCount;

    public Integer getTotalReadStoryNumber() {
        return TotalReadStoryNumber;
    }

    public void setTotalReadStoryNumber(Integer totalReadStoryNumber) {
        TotalReadStoryNumber = totalReadStoryNumber;
    }

    public Integer getTotalReadStoryDuration() {
        return TotalReadStoryDuration;
    }

    public void setTotalReadStoryDuration(Integer totalReadStoryDuration) {
        TotalReadStoryDuration = totalReadStoryDuration;
    }

    public Integer getTotalListenStoryNumber() {
        return TotalListenStoryNumber;
    }

    public void setTotalListenStoryNumber(Integer totalListenStoryNumber) {
        TotalListenStoryNumber = totalListenStoryNumber;
    }

    public Integer getTotalListenStoryDuration() {
        return TotalListenStoryDuration;
    }

    public void setTotalListenStoryDuration(Integer totalListenStoryDuration) {
        TotalListenStoryDuration = totalListenStoryDuration;
    }

    public Integer getTotalGameStoryNumber() {
        return TotalGameStoryNumber;
    }

    public void setTotalGameStoryNumber(Integer totalGameStoryNumber) {
        TotalGameStoryNumber = totalGameStoryNumber;
    }

    public Integer getTotalGameStoryDuration() {
        return TotalGameStoryDuration;
    }

    public void setTotalGameStoryDuration(Integer totalGameStoryDuration) {
        TotalGameStoryDuration = totalGameStoryDuration;
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
}
