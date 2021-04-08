package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;

import java.util.ArrayList;
import java.util.List;

public class StatisticWeekPlanLabel {

    List<WeekPlanLabel> readLabelList;
    List<WeekPlanLabel> cognitionLabelList;
    List<WeekPlanLabel> literacyLabelList;
    List<WeekPlanLabel> fiveAreaLabelList;

    public StatisticWeekPlanLabel() {
        this.readLabelList = new ArrayList<>();
        this.cognitionLabelList = new ArrayList<>();
        this.literacyLabelList = new ArrayList<>();
        this.fiveAreaLabelList = new ArrayList<>();
    }

    public List<WeekPlanLabel> getReadLabelList() {
        return readLabelList;
    }

    public void setReadLabelList(List<WeekPlanLabel> readLabelList) {
        this.readLabelList = readLabelList;
    }

    public List<WeekPlanLabel> getCognitionLabelList() {
        return cognitionLabelList;
    }

    public void setCognitionLabelList(List<WeekPlanLabel> cognitionLabelList) {
        this.cognitionLabelList = cognitionLabelList;
    }

    public List<WeekPlanLabel> getLiteracyLabelList() {
        return literacyLabelList;
    }

    public void setLiteracyLabelList(List<WeekPlanLabel> literacyLabelList) {
        this.literacyLabelList = literacyLabelList;
    }

    public List<WeekPlanLabel> getFiveAreaLabelList() {
        return fiveAreaLabelList;
    }

    public void setFiveAreaLabelList(List<WeekPlanLabel> fiveAreaLabelList) {
        this.fiveAreaLabelList = fiveAreaLabelList;
    }

    public void addWeekPlanLabel(WeekPlanLabel weekPlanLabel) {
        if(weekPlanLabel.getLabelType().equals(WeekPlanLabelStyle.READ_TYPE)) {
            this.readLabelList.add(weekPlanLabel);
        }
        if(weekPlanLabel.getLabelType().equals(WeekPlanLabelStyle.COGNITION_TYPE)) {
            this.cognitionLabelList.add(weekPlanLabel);
        }
        if(weekPlanLabel.getLabelType().equals(WeekPlanLabelStyle.LITERACY_TYPE)) {
            this.literacyLabelList.add(weekPlanLabel);
        }
        if(weekPlanLabel.getLabelType().equals(WeekPlanLabelStyle.FIVE_AREA)) {
            this.fiveAreaLabelList.add(weekPlanLabel);
        }
    }

}
