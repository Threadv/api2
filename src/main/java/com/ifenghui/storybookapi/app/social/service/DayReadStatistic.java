package com.ifenghui.storybookapi.app.social.service;

public class DayReadStatistic {

    Integer DayReadStoryNumber;

    Integer DayReadStoryDuration;

    Integer DayListenStoryNumber;

    Integer DayListenStoryDuration;

    Integer DayGameStoryNumber;

    Integer DayGameStoryDuration;

    Integer wordCount;

    Integer vocabularyCount;

    public Integer getDayReadStoryNumber() {
        return DayReadStoryNumber;
    }

    public void setDayReadStoryNumber(Integer dayReadStoryNumber) {
        DayReadStoryNumber = dayReadStoryNumber;
    }

    public Integer getDayReadStoryDuration() {
        return DayReadStoryDuration;
    }

    public void setDayReadStoryDuration(Integer dayReadStoryDuration) {
        DayReadStoryDuration = dayReadStoryDuration;
    }

    public Integer getDayListenStoryNumber() {
        return DayListenStoryNumber;
    }

    public void setDayListenStoryNumber(Integer dayListenStoryNumber) {
        DayListenStoryNumber = dayListenStoryNumber;
    }

    public Integer getDayListenStoryDuration() {
        return DayListenStoryDuration;
    }

    public void setDayListenStoryDuration(Integer dayListenStoryDuration) {
        DayListenStoryDuration = dayListenStoryDuration;
    }

    public Integer getDayGameStoryNumber() {
        return DayGameStoryNumber;
    }

    public void setDayGameStoryNumber(Integer dayGameStoryNumber) {
        DayGameStoryNumber = dayGameStoryNumber;
    }

    public Integer getDayGameStoryDuration() {
        return DayGameStoryDuration;
    }

    public void setDayGameStoryDuration(Integer dayGameStoryDuration) {
        DayGameStoryDuration = dayGameStoryDuration;
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
